package com.xzp.smartcampus.access_examine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.access_examine.constconfig.FLowConst;
import com.xzp.smartcampus.access_examine.mapper.AccessFlowMapper;
import com.xzp.smartcampus.access_examine.model.AccessFlowModel;
import com.xzp.smartcampus.access_examine.service.IAccessFlowService;
import com.xzp.smartcampus.access_examine.vo.AccessFlowSearchParam;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.ISelectUserService;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.human.vo.UserVo;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;


/**
 * @author SGS
 */
@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class AccessFlowService extends IsolationBaseService<AccessFlowMapper, AccessFlowModel> implements IAccessFlowService {

    @Resource
    private IStaffUserService userService;

    @Resource
    private ISelectUserService selectUserService;


    @Override
    public PageResult<AccessFlowModel> searchTodoAccessFlow(AccessFlowSearchParam searchParam,
                                                            Integer current, Integer pageSize) {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        return this.selectPage(new Page<>(current, pageSize),
                new QueryWrapper<AccessFlowModel>().eq("examine_id", userInfo.getUserId())
                        .eq("examine_status", FLowConst.PENDING)
                        .like(StringUtils.isNotBlank(searchParam.getFlowName()), "flow_name", searchParam.getFlowName())
                        .lt(StringUtils.isNotBlank(searchParam.getEndTime()), "end_time", searchParam.getEndTime())
                        .gt(StringUtils.isNotBlank(searchParam.getStartTime()), "start_time", searchParam.getStartTime())
        );
    }

    @Override
    public PageResult<AccessFlowModel> searchAlreadyAccessFlow(AccessFlowSearchParam searchParam,
                                                               Integer current, Integer pageSize) {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        return this.selectPage(new Page<>(current, pageSize),
                new QueryWrapper<AccessFlowModel>().eq("examine_id", userInfo.getUserId())
                        .in("examine_status", Arrays.asList(FLowConst.FINISH, FLowConst.CANCEL))
                        .like(StringUtils.isNotBlank(searchParam.getFlowName()), "flow_name", searchParam.getFlowName())
                        .lt(StringUtils.isNotBlank(searchParam.getEndTime()), "end_time", searchParam.getEndTime())
                        .gt(StringUtils.isNotBlank(searchParam.getStartTime()), "start_time", searchParam.getStartTime())
        );
    }

    @Override
    public PageResult<AccessFlowModel> searchMineAccessFlow(AccessFlowSearchParam searchParam,
                                                            Integer current, Integer pageSize) {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        return this.selectPage(new Page<>(current, pageSize),
                new QueryWrapper<AccessFlowModel>().eq("originator_id", userInfo.getUserId())
                        .like(StringUtils.isNotBlank(searchParam.getFlowName()), "flow_name", searchParam.getFlowName())
                        .lt(StringUtils.isNotBlank(searchParam.getEndTime()), "end_time", searchParam.getEndTime())
                        .gt(StringUtils.isNotBlank(searchParam.getStartTime()), "start_time", searchParam.getStartTime())
        );
    }

    @Override
    public AccessFlowModel createAccessFlow(AccessFlowModel flowModel) {
        if (StringUtils.isBlank(flowModel.getApplicantId())) {
            log.warn("ApplicantId is null");
            throw new SipException("请求人不能为空");
        }
        if (StringUtils.isBlank(flowModel.getExamineId())) {
            log.warn("ExamineId  is null");
            throw new SipException("未指定审核人");
        }
        UserVo userVo = selectUserService.getUserVoById(flowModel.getApplicantId());
        if (userVo == null) {
            log.warn("not find staffModel by id {}", flowModel.getApplicantId());
            throw new SipException("参数错误，找不到申请人 id " + flowModel.getApplicantId());
        }
        StaffModel examineUser = userService.selectById(flowModel.getExamineId());
        if (examineUser == null) {
            log.warn("not find examineUser by id {}", flowModel.getExamineId());
            throw new SipException("参数错误，找不到审核人 id " + flowModel.getExamineId());
        }

        flowModel.setId(SqlUtil.getUUId());
        flowModel.setExamineStatus(FLowConst.PENDING);
        // 创建人
        LoginUserInfo userInfo = UserContext.getLoginUser();
        flowModel.setOriginatorId(userInfo.getUserId());
        flowModel.setOriginatorType(userInfo.getUserType());
        flowModel.setOriginatorCode(userInfo.getUserNumber());
        flowModel.setOriginatorName(userInfo.getName());
        // 申请人
        flowModel.setApplicantId(userVo.getId());
        flowModel.setApplicantName(userVo.getName());
        flowModel.setApplicantType(userVo.getUserType());
        flowModel.setApplicantCode(userVo.getUserJobCode());
        // 审核人信息
        flowModel.setExamineId(examineUser.getId());
        flowModel.setExamineName(examineUser.getName());
        flowModel.setExamineType(examineUser.getUserType());

        if (StringUtils.isNotEmpty(flowModel.getCarNumber())) {
            flowModel.setIsCar(1);
        } else {
            flowModel.setIsCar(0);
        }
        this.insert(flowModel);

        return flowModel;
    }

    @Override
    public AccessFlowModel examineAccessFlow(AccessFlowModel accessFlowModel) {
        AccessFlowModel localModel = this.selectById(accessFlowModel.getId());
        if (localModel == null) {
            log.error("LocalModel is null by id {}", accessFlowModel.getId());
            throw new SipException("数据错误，找不到AccessFlowModel id " + accessFlowModel.getId());
        }
        localModel.setExamineStatus(accessFlowModel.getExamineStatus());
        localModel.setExamineDesc(accessFlowModel.getExamineDesc());
        localModel.setExamineTime(new Date());
        this.updateById(localModel);

        return localModel;
    }

}
