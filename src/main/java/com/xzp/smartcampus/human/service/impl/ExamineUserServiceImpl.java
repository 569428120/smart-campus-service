package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.human.mapper.ExamineUserMapper;
import com.xzp.smartcampus.human.model.ExamineUserModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IExamineUserService;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.human.vo.ExamineUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ExamineUserServiceImpl extends IsolationBaseService<ExamineUserMapper, ExamineUserModel> implements IExamineUserService {

    @Resource
    private IStaffUserService userService;

    /**
     * 获取门禁审核人列表
     *
     * @param classId 班级id
     * @param name    名称
     * @param number  编号
     * @return List<ExamineUserVo>
     */
    @Override
    public List<ExamineUserVo> getAccessExamineUserList(String classId, String name, String number) {
        List<ExamineUserModel> examineUserModels = this.selectList(new QueryWrapper<ExamineUserModel>()
                .eq("examine_type", ExamineUserVo.EXAMINE_TYPE_ACCESS)
                .eq(StringUtils.isNotBlank(classId), "service_id", classId)
        );
        if (CollectionUtils.isEmpty(examineUserModels)) {
            log.info("examineUserModels is null");
            return Collections.emptyList();
        }
        return this.toExamineUserVos(examineUserModels);
    }


    /**
     * 转换为vo
     *
     * @param examineUserModels 数据
     * @return List<ExamineUserVo>
     */
    private List<ExamineUserVo> toExamineUserVos(List<ExamineUserModel> examineUserModels) {
        if (CollectionUtils.isEmpty(examineUserModels)) {
            log.warn("examineUserModels is null");
            return Collections.emptyList();
        }
        List<String> userIds = examineUserModels.stream().map(ExamineUserModel::getUserId).collect(Collectors.toList());
        List<StaffModel> staffModels = userService.selectByIds(userIds);
        if (CollectionUtils.isEmpty(staffModels)) {
            log.info("not find staffModels by ids {}", userIds);
            return Collections.emptyList();
        }
        return staffModels.stream().map(item -> {
            ExamineUserVo userVo = new ExamineUserVo();
            userVo.setId(item.getId());
            userVo.setName(item.getName());
            userVo.setUserType(item.getUserType());
            userVo.setNumber(item.getUserJobCode());
            userVo.setContact(item.getContact());
            return userVo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取卡的审核人列表
     *
     * @param classId 班级id
     * @param name    名称
     * @param number  编号
     * @return List<ExamineUserVo>
     */
    @Override
    public List<ExamineUserVo> getCardExamineUserList(String classId, String name, String number) {
        List<ExamineUserModel> examineUserModels = this.selectList(new QueryWrapper<ExamineUserModel>()
                .eq("examine_type", ExamineUserVo.EXAMINE_TYPE_CARD)
                .eq(StringUtils.isNotBlank(classId), "service_id", classId)
        );
        if (CollectionUtils.isEmpty(examineUserModels)) {
            log.info("examineUserModels is null");
            return Collections.emptyList();
        }
        return this.toExamineUserVos(examineUserModels);
    }

    /**
     * 一键放学审核人列表
     *
     * @param classId 班级id
     * @param name    名称
     * @param number  编号
     * @return
     */
    @Override
    public List<ExamineUserVo> getLeaveSchoolExamineUserList(String classId, String name, String number) {
        List<ExamineUserModel> examineUserModels = this.selectList(new QueryWrapper<ExamineUserModel>()
                .eq("examine_type", ExamineUserVo.EXAMINE_TYPE_LEAVE_SCHOOL)
                .eq(StringUtils.isNotBlank(classId), "service_id", classId)
        );
        if (CollectionUtils.isEmpty(examineUserModels)) {
            log.info("examineUserModels is null");
            return Collections.emptyList();
        }
        return this.toExamineUserVos(examineUserModels);
    }
}
