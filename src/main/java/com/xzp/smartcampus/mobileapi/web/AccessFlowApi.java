package com.xzp.smartcampus.mobileapi.web;

import com.aliyun.oss.common.utils.DateUtil;
import com.xzp.smartcampus.access_examine.constconfig.FLowConst;
import com.xzp.smartcampus.access_examine.model.AccessFlowModel;
import com.xzp.smartcampus.access_examine.service.IAccessFlowService;
import com.xzp.smartcampus.access_examine.vo.AccessFlowSearchParam;
import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.DataUtil;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.IExamineUserService;
import com.xzp.smartcampus.human.service.IStudentGroupService;
import com.xzp.smartcampus.human.vo.ExamineUserVo;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;


/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi/access/approval")
@Slf4j
public class AccessFlowApi {

    @Resource
    private IAccessFlowService flowService;

    @Resource
    private IExamineUserService examineUserService;

    @Resource
    private IStudentGroupService groupService;

    /**
     * 我创建的
     *
     * @param searchParam 搜索条件
     * @param current     页
     * @param pageSize    数量
     * @return PageResult
     */
    @GetMapping("/my-create/gets/page")
    public ResponseEntity<PageResult> getMyCreateFlowList(AccessFlowSearchParam searchParam,
                                                          @RequestParam(value = "current") Integer current,
                                                          @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(flowService.searchMineAccessFlow(searchParam, current, pageSize));
    }

    /**
     * 待我审批的
     *
     * @param searchParam 搜索条件
     * @param current     页
     * @param pageSize    数量
     * @return PageResult
     */
    @GetMapping("/to-examine/gets/page")
    public ResponseEntity<PageResult> getToExamineFlowList(AccessFlowSearchParam searchParam,
                                                           @RequestParam(value = "current") Integer current,
                                                           @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(flowService.searchTodoAccessFlow(searchParam, current, pageSize));
    }

    /**
     * 我已经审批的
     *
     * @param searchParam 搜索条件
     * @param current     页
     * @param pageSize    数量
     * @return PageResult
     */
    @GetMapping("/reviewed/gets/page")
    public ResponseEntity<PageResult> getReviewedFlowList(AccessFlowSearchParam searchParam,
                                                          @RequestParam(value = "current") Integer current,
                                                          @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(flowService.searchAlreadyAccessFlow(searchParam, current, pageSize));
    }


    /**
     * 流程模板
     *
     * @return AccessFlowModel
     */
    @GetMapping("/template/gets")
    public ResponseEntity<AccessFlowModel> getAccessFlowTemplate() {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        AccessFlowModel flowModel = new AccessFlowModel();
        flowModel.setOriginatorId(userInfo.getUserId());
        flowModel.setOriginatorType(userInfo.getUserType());
        flowModel.setOriginatorCode(userInfo.getUserNumber());
        flowModel.setOriginatorName(userInfo.getName());

        flowModel.setFlowName(userInfo.getName() + "-门禁申请");
        flowModel.setFlowType(AccessFlowModel.FLOW_TYPE_ACCESS);

        return ResponseEntity.ok(flowModel);
    }

    /**
     * 查看流程详情
     *
     * @param flowId flowId
     * @return map
     */
    @GetMapping("/gets/gets-by-id")
    public ResponseEntity<Map<String, Object>> getAccessFlowDetail(String flowId) throws IllegalAccessException {
        if (StringUtils.isBlank(flowId)) {
            log.warn("flowId is null");
            throw new SipException("flowId 不能为空");
        }
        AccessFlowModel flowModel = flowService.selectById(flowId);
        if (flowModel == null) {
            log.warn("flowModel is null by flowId {}", flowId);
            throw new SipException("找不到对象，根据id " + flowId);
        }
        List<Map<String, Object>> records = new ArrayList<>(2);
        Map<String, Object> step1 = new HashMap<>(5);
        step1.put("stepName", "流程发起");
        step1.put("userName", flowModel.getOriginatorName());
        step1.put("stepStatus", "submit");
        step1.put("stepDesc", flowModel.getApplicantDesc());
        step1.put("stepTime", DateUtil.formatAlternativeIso8601Date(flowModel.getCreateTime()));
        records.add(step1);
        // 审核记录
        if (StringUtils.isNotBlank(flowModel.getExamineId())) {
            Map<String, Object> step2 = new HashMap<>(5);
            step2.put("stepName", "流程审批");
            step2.put("userName", flowModel.getExamineId());
            step2.put("stepStatus", flowModel.getExamineStatus());
            step2.put("stepDesc", flowModel.getExamineDesc());
            step2.put("stepTime", DateUtil.formatAlternativeIso8601Date(flowModel.getExamineTime()));
            records.add(step2);
        }
        Map<String, Object> detail = DataUtil.objectToMap(flowModel);
        detail.put("records", records);
        return ResponseEntity.ok(detail);
    }

    /**
     * 提交
     *
     * @param flowModel flowModel
     * @return String
     */
    @PostMapping("/submit")
    public ResponseEntity<String> submitFlowModel(@RequestBody AccessFlowModel flowModel) {
        if (StringUtils.isBlank(flowModel.getApplicantId())) {
            log.warn("applicantId is null");
            throw new SipException("申请人不能为空");
        }
        if (StringUtils.isBlank(flowModel.getApplicantDesc())) {
            log.warn("applicantDesc is null");
            throw new SipException("申请原因不能为空");
        }
        flowService.createAccessFlow(flowModel);
        return ResponseEntity.ok("ok");
    }

    /**
     * 撤销
     *
     * @param flowModel flowModel
     * @return String
     */
    @PostMapping("/recall")
    public ResponseEntity<String> recallFlowModel(@RequestBody AccessFlowModel flowModel) {
        if (StringUtils.isBlank(flowModel.getId()) || StringUtils.isBlank(flowModel.getExamineDesc())) {
            log.warn("id or examineDesc is null");
            throw new SipException("流程id和审核意见examineDesc不能为空");
        }
        flowModel.setExamineStatus(FLowConst.CANCEL);
        flowService.examineAccessFlow(flowModel);
        return ResponseEntity.ok("ok");
    }

    /**
     * 确认
     *
     * @param flowModel flowModel
     * @return String
     */
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmFlowModel(@RequestBody AccessFlowModel flowModel) {
        if (StringUtils.isBlank(flowModel.getId()) || StringUtils.isBlank(flowModel.getExamineDesc())) {
            log.warn("id or examineDesc is null");
            throw new SipException("流程id和审核意见examineDesc不能为空");
        }
        flowModel.setExamineStatus(FLowConst.FINISH);
        flowService.examineAccessFlow(flowModel);
        return ResponseEntity.ok("ok");
    }

    /**
     * 获取审核人列表
     *
     * @param classId 班级id
     * @param name    姓名
     * @param number  编号
     * @return List<ExamineUserVo>
     */
    @GetMapping("/examine-user/gets/gets-by-classid")
    public ResponseEntity<List<ExamineUserVo>> getAccessExamineUserList(String classId, String name, String number) {
        return ResponseEntity.ok(examineUserService.getAccessExamineUserList(classId, name, number));
    }

    /**
     * 申请人列表
     *
     * @param classId 班级id
     * @param name    姓名
     * @param number  编号
     * @return List<ExamineUserVo>
     */
    @GetMapping("/applicant-user/gets")
    public ResponseEntity<List<ExamineUserVo>> getApplicantUser(String classId, String name, String number) {
        if (StringUtils.isBlank(classId)) {
            log.warn("classId is null");
            throw new SipException("classId 不能为空");
        }
        List<StudentModel> studentModels = groupService.getStudentModelListByGroupId(classId, name, number);
        if (CollectionUtils.isEmpty(studentModels)) {
            log.info("studentModels is null");
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(studentModels.stream().map(item -> {
            ExamineUserVo userVo = new ExamineUserVo();
            userVo.setId(item.getId());
            userVo.setName(item.getName());
            userVo.setUserType(UserType.STUDENT.getKey());
            userVo.setNumber(item.getStudentCode());
            return userVo;
        }).collect(Collectors.toList()));
    }
}
