package com.xzp.smartcampus.mobileapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.human.service.IStudentGroupService;
import com.xzp.smartcampus.human.vo.ClassVo;
import com.xzp.smartcampus.mobileapi.enums.LSStatusType;
import com.xzp.smartcampus.mobileapi.mapper.LSRecordMapper;
import com.xzp.smartcampus.mobileapi.model.LSExamineUserModel;
import com.xzp.smartcampus.mobileapi.model.LSRecordModel;
import com.xzp.smartcampus.mobileapi.service.ILeaveSchoolService;
import com.xzp.smartcampus.mobileapi.service.ILsExamineUserService;
import com.xzp.smartcampus.mobileapi.vo.LSApprovalClassVo;
import com.xzp.smartcampus.mobileapi.vo.LSRecordSearchParam;
import com.xzp.smartcampus.mobileapi.vo.LSRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class LeaveSchoolServiceImpl extends IsolationBaseService<LSRecordMapper, LSRecordModel>
        implements ILeaveSchoolService {
    @Resource
    private IStudentGroupService studentGroupService;
    @Resource
    private ILsExamineUserService userService;
    @Resource
    private IStaffUserService staffUserService;


    /**
     * 将recordModels数据转化为LSRecordVos
     *
     * @param recordModels
     * @param groupModels
     * @return
     */
    private List<LSRecordVo> newLSRecordVos(List<LSRecordModel> recordModels, List<StudentGroupModel> groupModels) {
        List<LSRecordVo> lsRecordVos = new ArrayList<>();
        for (int i = 0; i < recordModels.size(); i++) {
            LSRecordVo recordVo = new LSRecordVo();
            BeanUtils.copyProperties(recordModels.get(i), recordVo);
            recordVo.setClassName(groupModels.get(i).getGroupName());
            lsRecordVos.add(recordVo);
        }
        return lsRecordVos;
    }

    /**
     * 一键放学历史记录查询
     *
     * @param param
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<LSRecordVo> selectLsRecord(LSRecordSearchParam param, Integer current, Integer pageSize) {

        PageResult<LSRecordModel> recordModelPageResult = this.selectPage(new Page<>(current, pageSize), new QueryWrapper<LSRecordModel>()
                .eq(StringUtils.isNotBlank(param.getClassId()), "class_id", param.getClassId())
                .gt(StringUtils.isNotBlank(param.getStartTime()), "leave_time", param.getStartTime())
                .lt(StringUtils.isNotBlank(param.getEndTime()), "leave_time", param.getEndTime())
        );
        List<LSRecordModel> lsRecordModels = recordModelPageResult.getData();
        if (CollectionUtils.isEmpty(lsRecordModels)) {
            log.info("LSRecordModel is null!");
            return new PageResult<>(0L, Collections.emptyList());
        }

        List<String> classIds = lsRecordModels.stream().map(LSRecordModel::getClassId).collect(Collectors.toList());
        // 查询Groups
        List<StudentGroupModel> groupModels = this.studentGroupService.selectByIds(classIds);
        List<LSRecordVo> lsRecordVos = this.newLSRecordVos(lsRecordModels, groupModels);

        return new PageResult<>(recordModelPageResult.getTotal(), lsRecordVos);
    }

    /**
     * 一键放学待审核列表查询
     *
     * @param param
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<LSRecordVo> selectLsApprovalRecord(LSRecordSearchParam param, Integer current, Integer pageSize) {

        PageResult<LSRecordModel> recordModelPageResult = this.selectPage(new Page<>(current, pageSize), new QueryWrapper<LSRecordModel>()
                .eq(StringUtils.isNotBlank(param.getClassId()), "class_id", param.getClassId())
                .gt(StringUtils.isNotBlank(param.getStartTime()), "leave_time", param.getStartTime())
                .lt(StringUtils.isNotBlank(param.getEndTime()), "leave_time", param.getEndTime())
                .eq("ls_status", LSStatusType.APPROVAL.getKey())
        );
        List<LSRecordModel> lsRecordModels = recordModelPageResult.getData();
        if (CollectionUtils.isEmpty(lsRecordModels)) {
            log.info("LSRecordModel is null!");
            return new PageResult<>(0L, Collections.emptyList());
        }

        List<String> classIds = lsRecordModels.stream().map(LSRecordModel::getClassId).collect(Collectors.toList());
        // 查询Groups
        List<StudentGroupModel> groupModels = this.studentGroupService.selectByIds(classIds);
        List<LSRecordVo> lsRecordVos = this.newLSRecordVos(lsRecordModels, groupModels);

        return new PageResult<>(recordModelPageResult.getTotal(), lsRecordVos);
    }

    private LSApprovalClassVo generateClassVo(StudentGroupModel groupModel) {
        LSApprovalClassVo classVo = new LSApprovalClassVo();
        classVo.setId(groupModel.getId());
        classVo.setName(groupModel.getGroupName());
        classVo.setNumber(groupModel.getGroupCode());
        return classVo;
    }

    /**
     * 一键放学班级审核列表
     *
     * @return List<ClassVo>
     */
    @Override
    public List<ClassVo> selectLsApprovalClasses() {
        return studentGroupService.getClassVoList();
    }


    /**
     * 一键放学审核人列表
     *
     * @return
     */
    @Override
    public List<StaffModel> selectLsExamineUsers() {
        List<LSExamineUserModel> userModels = this.userService.selectExamineUsers();
        if (userModels == null) {
            log.info("No Examine user!");
            return Collections.emptyList();
        }
        List<String> userIds = userModels.stream().map(LSExamineUserModel::getExamineUserId).collect(Collectors.toList());

        return this.staffUserService.selectByIds(userIds);
    }

    /**
     * 新增一键放学记录(状态默认为待审核)
     *
     * @param recordModel
     */
    @Override
    public void saveLsRecord(LSRecordModel recordModel) {
        recordModel.setLsStatus(LSStatusType.APPROVAL.getKey());
        this.insert(recordModel);
    }

    /**
     * 同意一键放学
     *
     * @param id
     */
    @Override
    public void confirmLsRecord(String id) {
        LSRecordModel recordModel = this.selectById(id);
        if (recordModel == null) {
            log.warn("No Leave school record of this id:" + id);
            return;
        }
        if (!recordModel.getLsStatus().equals(LSStatusType.APPROVAL.getKey())) {
            log.warn("This record is not in APPROVAL status!");
            return;
        }
        recordModel.setLsStatus(LSStatusType.AGREE.getKey());
        this.insert(recordModel);
    }

    /**
     * 拒绝一键放学
     *
     * @param id
     */
    @Override
    public void denyLsRecord(String id) {
        LSRecordModel recordModel = this.selectById(id);
        if (recordModel == null) {
            log.warn("No Leave school record of this id:" + id);
            return;
        }
        if (!recordModel.getLsStatus().equals(LSStatusType.APPROVAL.getKey())) {
            log.warn("This record is not in APPROVAL status!");
            return;
        }
        recordModel.setLsStatus(LSStatusType.DISAGREE.getKey());
        this.insert(recordModel);
    }

}
