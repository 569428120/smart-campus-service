package com.xzp.smartcampus.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.JsonUtils;
import com.xzp.smartcampus.common.utils.JwtUtil;
import com.xzp.smartcampus.human.mapper.ClassToUserMapper;
import com.xzp.smartcampus.human.mapper.StaffMapper;
import com.xzp.smartcampus.human.mapper.StudentGroupMapper;
import com.xzp.smartcampus.human.model.ClassToUserModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.portal.service.IAuthService;
import com.xzp.smartcampus.portal.vo.HisClassInfo;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import com.xzp.smartcampus.portal.vo.RegionInfo;
import com.xzp.smartcampus.portal.vo.SchoolInfo;
import com.xzp.smartcampus.system.mapper.RegionMapper;
import com.xzp.smartcampus.system.mapper.SchoolMapper;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.model.SchoolModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Resource
    private StaffMapper userMapper;

    @Resource
    private RegionMapper regionMapper;

    @Resource
    private SchoolMapper schoolMapper;

    @Resource
    private StudentGroupMapper studentGroupMapper;

    @Resource
    private ClassToUserMapper classToUserMapper;

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @param type     验证码
     * @return String
     */
    @Override
    public Map<String, Object> userLogin(String userName, String password, String type) {
        Map<String, Object> login = new HashMap<>();
        try {
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
                log.warn("userName or password is null");
                throw new SipException("账号和密码不能为空");
            }
            // 用户名账号登录支持职员
            List<StaffModel> staffModels = userMapper.selectList(new QueryWrapper<StaffModel>()
                    .eq("user_name", userName)
                    .eq("user_password", password)
            );
            if (CollectionUtils.isEmpty(staffModels)) {
                log.warn("not find user");
                throw new SipException("登录失败，账号或者密码错误");
            }
            Set<String> regionIds = staffModels.stream()
                    .filter(item -> StringUtils.isNotBlank(item.getRegionId()))
                    .map(StaffModel::getRegionId).collect(Collectors.toSet());
            Set<String> schoolIds = staffModels.stream()
                    .filter(item -> StringUtils.isNotBlank(item.getSchoolId()))
                    .map(StaffModel::getSchoolId).collect(Collectors.toSet());
            // 默认使用第一个找到的用户
            StaffModel currUser = staffModels.get(0);
            LoginUserInfo userInfo = new LoginUserInfo();
            userInfo.setUserId(currUser.getId());
            userInfo.setUserType(currUser.getUserType());
            userInfo.setUserNumber(currUser.getUserJobCode());
            userInfo.setName(currUser.getName());
            userInfo.setHisClass(this.getHisClassById(currUser.getId()));
            userInfo.setCurrRegionInfo(this.getRegionInfoById(currUser.getRegionId()));
            userInfo.setCurrSchoolInfo(this.getSchoolInfoById(currUser.getSchoolId()));
            userInfo.setRegionInfoList(this.getRegionInfoListByIds(regionIds));
            userInfo.setSchoolInfoList(this.getSchoolInfoListByIds(schoolIds));
            // 设置返回数据
            login.put("status", "ok");
            login.put("errorMsg", "");
            login.put("authentication", JwtUtil.sign(JsonUtils.toString(userInfo)));
            login.put("userInfo", userInfo);
        } catch (Exception e) {
            login.put("status", "error");
            login.put("errorMsg", e.getMessage());
        }
        return login;
    }

    /**
     * 返回所在管理的班级 TODO 需要根据用户类型区分处理
     *
     * @param userId 用户id
     * @return List<HisClassInfo>
     */
    private List<HisClassInfo> getHisClassById(String userId) {
        if (StringUtils.isBlank(userId)) {
            return Collections.emptyList();
        }
        List<ClassToUserModel> classToUserModels = classToUserMapper.selectList(new QueryWrapper<ClassToUserModel>()
                .eq("user_id", userId)
        );
        if (CollectionUtils.isEmpty(classToUserModels)) {
            log.info("not find classToUserModels by userId {}", userId);
            return Collections.emptyList();
        }
        List<String> classIds = classToUserModels.stream().map(ClassToUserModel::getClassId).collect(Collectors.toList());
        List<StudentGroupModel> groupModels = studentGroupMapper.selectList(new QueryWrapper<StudentGroupModel>()
                .in("id", classIds)
                .orderByDesc("grade_level", "group_name")
        );
        if (CollectionUtils.isEmpty(groupModels)) {
            log.info("groupModels not find by classIds {}", classIds);
            return Collections.emptyList();
        }
        return groupModels.stream().map(item -> {
            HisClassInfo classInfo = new HisClassInfo();
            classInfo.setClassId(item.getId());
            classInfo.setClassName(item.getGradeLevel() + "年级 " + item.getGroupName());
            return classInfo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询区域列表
     *
     * @param regionIds regionIds
     * @return List<RegionInfo>
     */
    private List<RegionInfo> getRegionInfoListByIds(Collection<String> regionIds) {
        if (CollectionUtils.isEmpty(regionIds)) {
            log.info("regionIds is null");
            return Collections.emptyList();
        }
        List<RegionModel> regionModels = regionMapper.selectBatchIds(regionIds);
        if (CollectionUtils.isEmpty(regionModels)) {
            log.info("not find regionModels by regionIds {}", regionIds);
            return Collections.emptyList();
        }
        return regionModels.stream().map(item -> {
            RegionInfo regionInfo = new RegionInfo();
            regionInfo.setRegionId(item.getId());
            regionInfo.setRegionName(item.getRegionName());
            regionInfo.setEducationName(item.getEducationName());
            return regionInfo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取学校列表
     *
     * @param schoolIds schoolIds
     * @return List<SchoolInfo>
     */
    private List<SchoolInfo> getSchoolInfoListByIds(Collection<String> schoolIds) {
        if (CollectionUtils.isEmpty(schoolIds)) {
            log.info("schoolIds is null");
            return Collections.emptyList();
        }
        List<SchoolModel> schoolModels = schoolMapper.selectBatchIds(schoolIds);
        if (CollectionUtils.isEmpty(schoolModels)) {
            log.info("not find schoolModels by schoolIds {}", schoolIds);
            return Collections.emptyList();
        }
        return schoolModels.stream().map(item -> {
            SchoolInfo schoolInfo = new SchoolInfo();
            schoolInfo.setSchoolId(item.getId());
            schoolInfo.setSchoolName(item.getSchoolName());
            schoolInfo.setSchoolType(item.getSchoolType());
            schoolInfo.setContact(item.getContact());
            return schoolInfo;
        }).collect(Collectors.toList());
    }

    /**
     * 学校id
     *
     * @param schoolId schoolId
     * @return SchoolInfo
     */
    private SchoolInfo getSchoolInfoById(String schoolId) {
        if (StringUtils.isBlank(schoolId)) {
            log.info("schoolId is null");
            return null;
        }
        List<SchoolInfo> schoolInfos = this.getSchoolInfoListByIds(Collections.singleton(schoolId));
        if (CollectionUtils.isEmpty(schoolInfos)) {
            log.info("not find schoolModel by id {}", schoolId);
            return null;
        }

        return schoolInfos.get(0);
    }

    /**
     * 获得区域
     *
     * @param regionId 区域id
     * @return RegionInfo
     */
    private RegionInfo getRegionInfoById(String regionId) {
        if (StringUtils.isBlank(regionId)) {
            log.info("regionId is null");
            return null;
        }
        List<RegionInfo> regionInfos = this.getRegionInfoListByIds(Collections.singleton(regionId));
        if (CollectionUtils.isEmpty(regionInfos)) {
            log.info("not find RegionInfo by id {}", regionId);
            return null;
        }
        return regionInfos.get(0);
    }

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return LoginUserInfo
     */
    @Override
    public LoginUserInfo getLoginUserByToken(String token) {
        return null;
    }
}
