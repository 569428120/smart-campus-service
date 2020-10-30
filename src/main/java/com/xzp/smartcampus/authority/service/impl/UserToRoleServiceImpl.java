package com.xzp.smartcampus.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.authority.mapper.UserToRoleMapper;
import com.xzp.smartcampus.authority.model.RoleModel;
import com.xzp.smartcampus.authority.model.UserToRoleModel;
import com.xzp.smartcampus.authority.service.IRoleService;
import com.xzp.smartcampus.authority.service.IRoleToMenuService;
import com.xzp.smartcampus.authority.service.IUserToRoleService;
import com.xzp.smartcampus.authority.vo.LoginUserToRoleVo;
import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.common.service.NonIsolationBaseService;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.human.mapper.StaffMapper;
import com.xzp.smartcampus.human.mapper.StudentContactMapper;
import com.xzp.smartcampus.human.mapper.StudentMapper;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.mobileapi.vo.AppInfo;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import com.xzp.smartcampus.system.enums.MenuType;
import com.xzp.smartcampus.system.mapper.RegionMapper;
import com.xzp.smartcampus.system.mapper.SchoolMapper;
import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.model.SchoolModel;
import com.xzp.smartcampus.system.service.IMobileMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserToRoleServiceImpl extends NonIsolationBaseService<UserToRoleMapper, UserToRoleModel> implements IUserToRoleService {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private StudentContactMapper studentContactMapper;

    @Resource
    private RegionMapper regionMapper;

    @Resource
    private SchoolMapper schoolMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private IRoleToMenuService roleToMenuService;

    @Resource
    private IRoleService roleService;


    /**
     * 获得角色列表，根据bindUserId
     *
     * @param cellNumber 手机号码
     * @return loginResultVo
     */
    @Override
    public List<LoginUserToRoleVo> getRolesByCellNumber(@NotBlank(message = "手机号码不能为空") String cellNumber) {
        List<LoginUserToRoleVo> userToRoleVos = new ArrayList<>();
        // 职工数据
        List<StaffModel> staffModels = staffMapper.selectList(new QueryWrapper<StaffModel>()
                .eq("contact", cellNumber)
        );
        if (!CollectionUtils.isEmpty(staffModels)) {
            // 教育局
            List<StaffModel> bureauStaffModels = staffModels.stream()
                    .filter(item -> StringUtils.isNotBlank(item.getRegionId()) && StringUtils.isBlank(item.getSchoolId()))
                    .collect(Collectors.toList());
            LoginUserToRoleVo regGroupRoleVo = this.getRegionRoleList(bureauStaffModels);
            if (regGroupRoleVo != null) {
                userToRoleVos.add(regGroupRoleVo);
            }
            // 老师的
            List<StaffModel> tStaffModels = staffModels.stream()
                    .filter(item -> StringUtils.isNotBlank(item.getRegionId()) && StringUtils.isNotBlank(item.getSchoolId()))
                    .collect(Collectors.toList());
            LoginUserToRoleVo tGroupRoleVo = this.getTeacherRoleList(tStaffModels);
            if (tGroupRoleVo != null) {
                userToRoleVos.add(tGroupRoleVo);
            }
        }
        // 家长或者学生
        List<StudentContactModel> studentContactModels = studentContactMapper.selectList(new QueryWrapper<StudentContactModel>()
                .eq("contact", cellNumber)
        );
        if (!CollectionUtils.isEmpty(studentContactModels)) {
            LoginUserToRoleVo studentRoleVo = this.getStudentRoleList(studentContactModels);
            if (studentRoleVo != null) {
                userToRoleVos.add(studentRoleVo);
            }
        }
        return userToRoleVos;
    }

    /**
     * 学生
     *
     * @param studentContactModels studentContactModels
     * @return LoginUserToRoleVo
     */
    private LoginUserToRoleVo getStudentRoleList(List<StudentContactModel> studentContactModels) {
        if (CollectionUtils.isEmpty(studentContactModels)) {
            log.info("studentContactModels is null");
            return null;
        }
        List<String> schoolIds = studentContactModels.stream().map(StudentContactModel::getSchoolId).collect(Collectors.toList());
        Map<String, SchoolModel> schoolIdToModelMap = this.getIdToSchoolModelMap(schoolIds);
        if (CollectionUtils.isEmpty(schoolIdToModelMap)) {
            log.warn("schoolIdToModelMap is null");
            return null;
        }
        List<String> studentIds = studentContactModels.stream().map(StudentContactModel::getStudentId).collect(Collectors.toList());
        Map<String, StudentModel> studentIdToModelMap = this.getStudentIdToModelMap(studentIds);
        LoginUserToRoleVo roleVo = new LoginUserToRoleVo();
        roleVo.setName("我是家长");
        roleVo.setChildren(studentContactModels.stream()
                .filter(item -> schoolIdToModelMap.containsKey(item.getSchoolId()) && studentIdToModelMap.containsKey(item.getStudentId()))
                .map(item -> {
                    LoginUserToRoleVo vo = new LoginUserToRoleVo();
                    vo.setUserId(item.getStudentId());
                    vo.setUserType(UserType.PARENT.getKey());
                    vo.setName(schoolIdToModelMap.get(item.getSchoolId()).getSchoolName() + "-" + studentIdToModelMap.get(item.getStudentId()).getName());
                    return vo;
                }).collect(Collectors.toList()));
        return roleVo;
    }

    /**
     * id映射学生
     *
     * @param studentIds studentIds
     * @return StudentModel
     */
    private Map<String, StudentModel> getStudentIdToModelMap(List<String> studentIds) {
        if (CollectionUtils.isEmpty(studentIds)) {
            log.warn("studentIds is null");
            return Collections.emptyMap();
        }
        List<StudentModel> studentModels = studentMapper.selectBatchIds(studentIds);
        if (CollectionUtils.isEmpty(studentModels)) {
            log.info("not find studentModels by ids {}", studentIds);
            return Collections.emptyMap();
        }
        return studentModels.stream().collect(Collectors.toMap(StudentModel::getId, v -> v));
    }

    /**
     * id映射教育局
     *
     * @param regIds id
     * @return 映射
     */
    private Map<String, RegionModel> getIdToRegionModelMap(List<String> regIds) {
        if (CollectionUtils.isEmpty(regIds)) {
            log.warn("regIds is null");
            return Collections.emptyMap();
        }
        List<RegionModel> regionModels = regionMapper.selectBatchIds(regIds);
        if (CollectionUtils.isEmpty(regionModels)) {
            log.warn("not find regionModels by ids {}", regIds);
            return Collections.emptyMap();
        }
        return regionModels.stream().collect(Collectors.toMap(RegionModel::getId, v -> v));
    }

    /**
     * 教育局角色
     *
     * @param staffModels staffModels
     * @return LoginUserToRoleVo
     */
    private LoginUserToRoleVo getRegionRoleList(List<StaffModel> staffModels) {
        if (CollectionUtils.isEmpty(staffModels)) {
            log.info("staffModels is null");
            return null;
        }
        List<String> regIds = staffModels.stream().map(StaffModel::getRegionId).collect(Collectors.toList());
        Map<String, RegionModel> idToRegionModelMap = this.getIdToRegionModelMap(regIds);
        LoginUserToRoleVo bureauRoleVos = new LoginUserToRoleVo();
        bureauRoleVos.setName("教育局工作人员");
        bureauRoleVos.setChildren(staffModels.stream()
                .filter(item -> idToRegionModelMap.containsKey(item.getRegionId()))
                .map(item -> {
                    LoginUserToRoleVo vo = new LoginUserToRoleVo();
                    vo.setUserId(item.getId());
                    vo.setUserType(item.getUserType());
                    if (idToRegionModelMap.containsKey(item.getRegionId())) {
                        vo.setName(idToRegionModelMap.get(item.getRegionId()).getEducationName());
                    }
                    return vo;
                }).collect(Collectors.toList()));
        return bureauRoleVos;
    }

    /**
     * 学校
     *
     * @param schoolIds schoolIds
     * @return 映射
     */
    @Override
    public Map<String, SchoolModel> getIdToSchoolModelMap(List<String> schoolIds) {
        if (CollectionUtils.isEmpty(schoolIds)) {
            log.warn("schoolIds is null");
            return Collections.emptyMap();
        }
        List<SchoolModel> schoolModels = schoolMapper.selectBatchIds(schoolIds);
        if (CollectionUtils.isEmpty(schoolModels)) {
            log.info("not find schoolModels by ids {}", schoolIds);
            return Collections.emptyMap();
        }
        return schoolModels.stream().collect(Collectors.toMap(SchoolModel::getId, v -> v));
    }

    /**
     * 根据用户id查询app列表
     *
     * @param userInfo 用户id
     * @return List<AppInfo>
     */
    @Override
    public List<AppInfo> getAppInfosByUserId(LoginUserInfo userInfo) {
        List<UserToRoleModel> userToRoleModels = this.selectList(new QueryWrapper<UserToRoleModel>()
                .eq("user_id", userInfo.getUserId())
        );
        // 使用默认的权限
        if (CollectionUtils.isEmpty(userToRoleModels)) {
            return this.getDefaultAppInfoList(userInfo);
        }
        // 角色权限
        List<String> roleIds = userToRoleModels.stream().map(UserToRoleModel::getAtyRoleId).collect(Collectors.toList());
        return roleToMenuService.getAppInfoByRoleIds(roleIds);
    }

    /**
     * 查询默认权限，默认老师，职工，和家长（学生）
     *
     * @param userInfo 用户id
     * @return List<AppInfo>
     */
    private List<AppInfo> getDefaultAppInfoList(LoginUserInfo userInfo) {
        String userType = userInfo.getUserType();
        // 默认使用家长权限
        String roleType = RoleModel.ROLE_TYPE_DEFAULT_STUDENT;
        // 老师和职员使用一套默认权限
        if (UserType.TEACHER.getKey().equals(userType) || UserType.STAFF.getKey().equals(userType)) {
            roleType = RoleModel.ROLE_TYPE_DEFAULT_TEACHER;
        }
        List<String> roleIds = new ArrayList<>();
        List<RoleModel> roleModels = roleService.selectList(new QueryWrapper<RoleModel>()
                .eq("role_type", roleType)
        );
        if (!CollectionUtils.isEmpty(roleModels)) {
            roleIds.addAll(roleModels.stream().map(RoleModel::getId).collect(Collectors.toList()));
        }
        return roleToMenuService.getAppInfoByRoleIds(roleIds);
    }

    /**
     * 老师的角色
     *
     * @param staffModels staffModels
     * @return LoginUserToRoleVo
     */
    private LoginUserToRoleVo getTeacherRoleList(List<StaffModel> staffModels) {
        if (CollectionUtils.isEmpty(staffModels)) {
            log.info("staffModels is null");
            return null;
        }
        List<String> schoolIds = staffModels.stream().map(StaffModel::getSchoolId).collect(Collectors.toList());
        Map<String, SchoolModel> schoolIdToModelMap = this.getIdToSchoolModelMap(schoolIds);
        LoginUserToRoleVo tRoleVos = new LoginUserToRoleVo();
        tRoleVos.setName("学校职工(老师)");
        tRoleVos.setChildren(staffModels.stream()
                .filter(item -> schoolIdToModelMap.containsKey(item.getSchoolId()))
                .map(item -> {
                    LoginUserToRoleVo vo = new LoginUserToRoleVo();
                    vo.setUserId(item.getId());
                    vo.setUserType(item.getUserType());
                    if (schoolIdToModelMap.containsKey(item.getSchoolId())) {
                        vo.setName(schoolIdToModelMap.get(item.getSchoolId()).getSchoolName());
                    }
                    return vo;
                }).collect(Collectors.toList()));
        return tRoleVos;
    }
}
