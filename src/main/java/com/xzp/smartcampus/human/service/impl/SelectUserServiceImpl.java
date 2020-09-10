package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.*;
import com.xzp.smartcampus.human.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SelectUserServiceImpl implements ISelectUserService {

    @Resource
    private IStudentGroupService studentGroupService;

    @Resource
    private IStudentService studentService;

    @Resource
    private IStaffGroupService staffGroupService;

    @Resource
    private IStaffUserService userService;

    @Resource
    private IStudentContactService contactService;

    /**
     * 根据用户类型获取用户分组
     *
     * @param searchValue 搜索条件
     * @param userType    用户类型
     * @return List<UserGroupTreeVo>
     */
    @Override
    public List<UserGroupTreeVo> getUserGroupTreeList(UserGroupTreeVo searchValue, String userType) {
        if (StringUtils.isBlank(userType)) {
            log.warn("userType is null");
            return Collections.emptyList();
        }
        // 查询学生的分组信息
        if (UserType.STUDENT.getKey().equals(userType) || UserType.PARENT.getKey().equals(userType)) {
            return studentGroupService.getUserGroupTreeList(searchValue);
        }
        return staffGroupService.getUserGroupTreeVoList(searchValue);
    }

    /**
     * 分页查询用户
     *
     * @param searchValue searchValue
     * @param groupId     groupId
     * @param userType    userType
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<UserVo>
     */
    @Override
    public PageResult<UserVo> getUserList(UserVo searchValue, String groupId, String userType, Integer current, Integer pageSize) {
        if (StringUtils.isBlank(userType)) {
            log.warn("userType is null");
            throw new SipException("参数错误，userType不能为空");
        }
        // 学生列表
        if (UserType.STUDENT.getKey().equals(userType)) {
            return studentService.getUserVoPage(searchValue, groupId, current, pageSize);
        }
        // 家长列表
        if (UserType.PARENT.getKey().equals(userType)) {
            return studentService.getParentUserVoPage(searchValue, groupId, current, pageSize);
        }
        searchValue.setUserType(userType);
        searchValue.setGroupId(groupId);
        return userService.getUserVoListPage(searchValue, current, pageSize);
    }

    /**
     * 过滤名称和身份证号(学号) TODO 该处查询速度较慢需要优化
     *
     * @param searchValue searchValue
     * @return List<String>
     */
    @Override
    public List<String> getUserIds(IFeatureVo searchValue) {
        if (StringUtils.isBlank(searchValue.getUserType())
                && StringUtils.isBlank(searchValue.getName())
                && StringUtils.isBlank(searchValue.getUserCode())
                && StringUtils.isBlank(searchValue.getGroupId())) {
            return Collections.emptyList();
        }
        List<String> userIds = new ArrayList<>();
        userIds.add("-1");
        // 用户
        List<StaffModel> staffModels = this.getStaffModels(searchValue);
        if (!CollectionUtils.isEmpty(staffModels)) {
            userIds.addAll(staffModels.stream().map(StaffModel::getId).collect(Collectors.toList()));
        }
        // 学生
        List<StudentModel> studentModels = this.getStudentModels(searchValue);
        if (!CollectionUtils.isEmpty(studentModels)) {
            userIds.addAll(studentModels.stream().map(StudentModel::getId).collect(Collectors.toList()));
        }
        // 家长
        List<StudentContactModel> contactModels = this.getStudentContactModels(searchValue);
        if (!CollectionUtils.isEmpty(contactModels)) {
            userIds.addAll(contactModels.stream().map(StudentContactModel::getId).collect(Collectors.toList()));
        }
        return userIds;
    }

    /**
     * 转换为特征值vo
     *
     * @param featureVos featureVos
     * @return List<IFeatureVo>
     */
    @Override
    public List<IFeatureVo> toFeatureCardVoList(List<? extends IModelToFeatureVo> featureVos) {
        if (CollectionUtils.isEmpty(featureVos)) {
            return Collections.emptyList();
        }
        List<String> userIds = featureVos.stream().map(IModelToFeatureVo::getUserId).collect(Collectors.toList());
        Map<String, UserVo> userIdToUserVoMap = userService.getUserVoListByIds(userIds).stream().collect(Collectors.toMap(UserVo::getId, v -> v));
        Map<String, StudentVo> studentIdToStudentVoMap = studentService.getStudentVoListByIds(userIds).stream().collect(Collectors.toMap(StudentVo::getId, v -> v));
        Map<String, UserVo> userIdToParentUserVoMap = studentService.getParentUserVoListByIds(userIds).stream().collect(Collectors.toMap(UserVo::getId, v -> v));

        return featureVos.stream().map(item -> {
            IFeatureVo featureVo = item.toFeatureVo();
            featureVo.setGroupName("缺失");
            featureVo.setName("缺失");
            featureVo.setUserCode("缺失");
            if (userIdToUserVoMap.containsKey(item.getUserId())) {
                UserVo userVo = userIdToUserVoMap.get(item.getUserId());
                featureVo.setGroupName(userVo.getGroupName());
                featureVo.setUserCode(userVo.getUserIdentity());
                featureVo.setName(userVo.getName());
                featureVo.setUserType(userVo.getUserType());
                featureVo.setUserVo(userVo);
            }
            if (studentIdToStudentVoMap.containsKey(item.getUserId())) {
                StudentVo studentVo = studentIdToStudentVoMap.get(item.getUserId());
                featureVo.setGroupName(studentVo.getGroupName());
                featureVo.setUserCode(studentVo.getStudentCode());
                featureVo.setName(studentVo.getName());
                featureVo.setUserType(UserType.STUDENT.getKey());
                featureVo.setUserVo(this.studentVoToUserVo(studentVo));
            }
            if (userIdToParentUserVoMap.containsKey(item.getUserId())) {
                UserVo userVo = userIdToParentUserVoMap.get(item.getUserId());
                featureVo.setUserCode(userVo.getUserIdentity());
                featureVo.setGroupName(userVo.getGroupName());
                featureVo.setName(userVo.getName());
                featureVo.setUserType(UserType.PARENT.getKey());
                featureVo.setUserVo(userVo);
            }
            return featureVo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取用户vo
     *
     * @param userId 用户id 学生或者老师
     * @return UserVo
     */
    @Override
    public UserVo getUserVoById(@NotBlank String userId) {
        List<StudentVo> studentVos = studentService.getStudentVoListByIds(Collections.singletonList(userId));
        if (!CollectionUtils.isEmpty(studentVos)) {
            return this.studentVoToUserVo(studentVos.get(0));
        }
        List<UserVo> userVos = userService.getUserVoListByIds(Collections.singletonList(userId));
        if (!CollectionUtils.isEmpty(userVos)) {
            return userVos.get(0);
        }
        return null;
    }

    /**
     * studentVo 转换为userVo
     *
     * @param studentVo studentVo
     * @return UserVo
     */
    private UserVo studentVoToUserVo(StudentVo studentVo) {
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(studentVo, vo);
        vo.setUserIdentity(studentVo.getStudentCode());
        return vo;
    }

    /**
     * 家长
     *
     * @param searchValue searchValue
     * @return List<StudentContactModel>
     */
    private List<StudentContactModel> getStudentContactModels(IFeatureVo searchValue) {
        if (StringUtils.isBlank(searchValue.getUserType()) && StringUtils.isBlank(searchValue.getName()) && StringUtils.isBlank(searchValue.getUserCode())) {
            return Collections.emptyList();
        }
        if (StringUtils.isNotBlank(searchValue.getUserType()) && !UserType.PARENT.getKey().equals(searchValue.getUserType())) {
            return Collections.emptyList();
        }
        return contactService.selectList(new QueryWrapper<StudentContactModel>()
                .like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName())
                .like(StringUtils.isNotBlank(searchValue.getUserCode()), "contact", searchValue.getUserCode())
        );
    }

    /**
     * 获取学生列表
     *
     * @param searchValue searchValue
     * @return List<StudentModel>
     */
    private List<StudentModel> getStudentModels(IFeatureVo searchValue) {
        if (StringUtils.isBlank(searchValue.getUserType())
                && StringUtils.isBlank(searchValue.getName())
                && StringUtils.isBlank(searchValue.getUserCode())
                && StringUtils.isBlank(searchValue.getGroupId())) {
            return Collections.emptyList();
        }
        if (StringUtils.isNotBlank(searchValue.getUserType()) && !UserType.STUDENT.getKey().equals(searchValue.getUserType())) {
            return Collections.emptyList();
        }
        return studentService.selectList(new QueryWrapper<StudentModel>()
                .eq(StringUtils.isNotBlank(searchValue.getGroupId()), "group_id", searchValue.getGroupId())
                .like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName())
                .like(StringUtils.isNotBlank(searchValue.getUserCode()), "student_code", searchValue.getUserCode())
        );
    }

    /**
     * 查询职员用户
     *
     * @param searchValue searchValue
     * @return List<StaffModel>
     */
    private List<StaffModel> getStaffModels(IFeatureVo searchValue) {
        // 没有过滤条件则不进入
        if (StringUtils.isBlank(searchValue.getUserType())
                && StringUtils.isBlank(searchValue.getName())
                && StringUtils.isBlank(searchValue.getUserCode())
                && StringUtils.isBlank(searchValue.getGroupId())) {
            return Collections.emptyList();
        }
        if (StringUtils.isNotBlank(searchValue.getUserType()) && !UserType.STAFF.getKey().equals(searchValue.getUserType()) && !UserType.TEACHER.getKey().equals(searchValue.getUserType())) {
            return Collections.emptyList();
        }
        return userService.selectList(new QueryWrapper<StaffModel>()
                .eq(StringUtils.isNotBlank(searchValue.getUserType()), "user_type", searchValue.getUserType())
                .eq(StringUtils.isNotBlank(searchValue.getGroupId()), "group_id", searchValue.getGroupId())
                .like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName())
                .like(StringUtils.isNotBlank(searchValue.getUserCode()), "user_identity", searchValue.getUserCode())
        );
    }
}
