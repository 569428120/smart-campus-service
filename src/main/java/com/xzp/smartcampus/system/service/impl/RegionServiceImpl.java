package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.NonIsolationBaseService;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.StaffGroupMapper;
import com.xzp.smartcampus.human.mapper.StaffMapper;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffGroupService;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.system.mapper.RegionMapper;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.service.IRegionService;
import com.xzp.smartcampus.system.vo.RegionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RegionServiceImpl extends NonIsolationBaseService<RegionMapper, RegionModel> implements IRegionService {

    @Resource
    private StaffMapper userMapper;

    @Resource
    private StaffGroupMapper groupMapper;

    @Resource
    private IStaffGroupService staffGroupService;

    @Resource
    private IStaffUserService userService;

    /**
     * 分页查询教育局数据
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return PageResult
     */
    @Override
    public PageResult getRegionListPage(RegionModel searchValue, Integer current, Integer pageSize) {
        PageResult<RegionModel> pageResult = this.selectPage(new Page<>(current, pageSize), new QueryWrapper<RegionModel>()
                .like(StringUtils.isNotBlank(searchValue.getRegionName()), "region_name", searchValue.getRegionName())
                .like(StringUtils.isNotBlank(searchValue.getEducationName()), "education_name", searchValue.getEducationName())
                .orderByDesc("create_time")
        );
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), this.toRegionVoList(pageResult.getData()));
    }

    /**
     * 转换为vo对象
     *
     * @param data data
     * @return List<RegionVo>
     */
    private List<RegionVo> toRegionVoList(List<RegionModel> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        List<StaffModel> staffModels = userMapper.selectBatchIds(data.stream().map(RegionModel::getAdminUserId).collect(Collectors.toList()));
        Map<String, StaffModel> userIdToModelMap = CollectionUtils.isEmpty(staffModels) ? Collections.emptyMap() : staffModels.stream().collect(Collectors.toMap(StaffModel::getId, v -> v));
        return data.stream().map(item -> {
            RegionVo vo = new RegionVo();
            BeanUtils.copyProperties(item, vo);
            StaffModel admin = userIdToModelMap.get(item.getAdminUserId());
            if (admin != null) {
                vo.setUserName(admin.getUserName());
                vo.setPassword(admin.getUserPassword());
                vo.setContact(admin.getContact());
            }
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 保存教育局数据
     *
     * @param regionVo regionModel
     * @return RegionModel
     */
    @Override
    public RegionModel postRegionModel(RegionVo regionVo) {
        // 新增操作
        if (StringUtils.isBlank(regionVo.getId())) {
            regionVo.setId(SqlUtil.getUUId());
            regionVo.setRegionId(regionVo.getId());
            StaffModel admin = this.createAdminUserGroup(regionVo.getAuthorityTemplateId(), regionVo);
            regionVo.setAdminUserId(admin.getId());
            this.insert(regionVo);
            return regionVo;
        }
        // 更新管理员用户
        StaffModel staffModel = this.updateAdminUser(regionVo.getAdminUserId(), regionVo);
        regionVo.setAdminUserId(staffModel.getId());
        this.updateRegionModel(regionVo);
        return regionVo;
    }

    /**
     * 更新用户数据
     *
     * @param adminUserId adminUserId
     */
    private StaffModel updateAdminUser(String adminUserId, RegionVo regionVo) {
        return this.updateAdminUser(regionVo.getId(), regionVo.getSchoolId(), adminUserId, regionVo.getAuthorityTemplateId(), regionVo.getPassword(), regionVo.getContact());
    }

    /**
     * 数据校验
     *
     * @param regionVo regionVo
     */
    @Override
    public void validatorRegion(RegionVo regionVo) {
        // 管理员用户用户名不能存在（不做数据隔离）
        if (StringUtils.isNotBlank(regionVo.getUserName())) {
            List<StaffModel> userList = userMapper.selectList(new QueryWrapper<StaffModel>()
                    .eq("user_name", regionVo.getUserName())
                    .notIn(StringUtils.isNotBlank(regionVo.getAdminUserId()), "id", regionVo.getAdminUserId())
            );
            if (!CollectionUtils.isEmpty(userList)) {
                log.warn("exist user {}", userList.get(0));
                throw new SipException(MessageFormat.format("{0} 用户已经存在", regionVo.getUserName()));
            }
        }

    }

    /**
     * 创建管理员用户
     *
     * @param staffGroupModel 区域id
     * @param password        登录密码
     * @param contact         手机号
     * @return StaffModel
     */
    @Override
    public StaffModel createAdminUserGroup(StaffGroupModel staffGroupModel, String password, String contact) {
        if (StringUtils.isBlank(contact) || StringUtils.isBlank(password)) {
            throw new SipException("authorityTemplate or userName or password is null");
        }
        StaffModel staffModel = new StaffModel();
        staffModel.setId(SqlUtil.getUUId());
        staffModel.setName("系统预制管理员");
        staffModel.setUserName(contact);
        staffModel.setUserPassword(password);
        staffModel.setContact(contact);
        staffModel.setGroupId(staffGroupModel.getId());
        staffModel.setRegionId(staffGroupModel.getRegionId());
        staffModel.setSchoolId(staffGroupModel.getSchoolId());
        userService.insert(staffModel);
        return staffModel;
    }

    private StaffGroupModel createGroupModel(String regionId, String schoolId, String authorityTemplateId) {
        StaffGroupModel staffGroupModel = new StaffGroupModel();
        staffGroupModel.setId(SqlUtil.getUUId());
        staffGroupModel.setAuthorityId(authorityTemplateId);
        staffGroupModel.setGroupName("管理员");
        staffGroupModel.setGroupCode(staffGroupModel.getId());
        staffGroupModel.setPid(Constant.ROOT);
        staffGroupModel.setTreePath(Constant.ROOT + Constant.TREE_SEPARATOR + staffGroupModel.getId());
        staffGroupModel.setDescription("系统自动生成的管理员用户");
        staffGroupModel.setRegionId(regionId);
        staffGroupModel.setSchoolId(schoolId);
        staffGroupService.insert(staffGroupModel);
        return staffGroupModel;
    }

    /**
     * 更新管理员用户信息
     *
     * @param userId              用户id
     * @param authorityTemplateId 权限模板id
     * @param password            密码
     * @param contact             手机号码
     */
    @Override
    public StaffModel updateAdminUser(String regionId, String schoolId, String userId, String authorityTemplateId, String password, String contact) {
        if (StringUtils.isBlank(userId)) {
            log.warn("userId is null");
            throw new SipException("参数错误，用户id不能为空");
        }
        List<StaffGroupModel> groupModels = groupMapper.selectList(new QueryWrapper<StaffGroupModel>()
                .eq(StringUtils.isNotBlank(regionId), "region_id", regionId)
                .eq(StringUtils.isNotBlank(schoolId), "school_id", schoolId)
                .eq("group_name", "管理员")
        );
        StaffModel adminUser = userMapper.selectById(userId);
        StaffGroupModel adminGroup = CollectionUtils.isEmpty(groupModels) ? this.createGroupModel(regionId, schoolId, authorityTemplateId) : groupModels.get(0);
        if (adminUser == null) {
            log.info("not find adminUser by adminUserId {},create new adminUser", userId);
            adminUser = this.createAdminUserGroup(adminGroup, password, contact);
        }
        adminUser.setGroupId(adminGroup.getId());
        if (StringUtils.isNotBlank(password)) {
            adminUser.setUserPassword(password);
        }
        if (StringUtils.isNotBlank(contact)) {
            adminUser.setContact(contact);
            adminUser.setUserName(contact);
        }
        if (StringUtils.isNotBlank(authorityTemplateId)) {
            adminGroup.setAuthorityId(authorityTemplateId);
        }
        userMapper.updateById(adminUser);
        staffGroupService.updateById(adminGroup);
        return adminUser;
    }

    /**
     * 创建管理
     *
     * @param regionId            regionId
     * @param schoolId            schoolId
     * @param authorityTemplateId authorityTemplateId
     * @param password            password
     * @param contact             contact
     * @return StaffModel
     */
    @Override
    public StaffModel createAdminUserGroup(String regionId, String schoolId, String authorityTemplateId, String password, String contact) {
        StaffGroupModel groupModel = this.createGroupModel(regionId, schoolId, authorityTemplateId);
        return this.createAdminUserGroup(groupModel, password, contact);
    }

    /**
     * 创建管理员用户组
     *
     * @param authorityTemplateId authorityTemplate
     */
    private StaffModel createAdminUserGroup(String authorityTemplateId, RegionVo regionVo) {
        StaffGroupModel groupModel = this.createGroupModel(regionVo.getId(), regionVo.getSchoolId(), authorityTemplateId);
        return this.createAdminUserGroup(groupModel, regionVo.getPassword(), regionVo.getContact());
    }

    /**
     * 更新数据
     *
     * @param regionModel regionModel
     */
    private void updateRegionModel(RegionModel regionModel) {
        if (StringUtils.isBlank(regionModel.getId())) {
            log.warn("regionModel id is null");
            return;
        }
        // 更新操作
        RegionModel localDbModel = this.selectById(regionModel.getId());
        if (localDbModel == null) {
            log.error("RegionModel not find id by {}", regionModel.getId());
            throw new SipException("数据异常，RegionModel的数据不存在 id " + regionModel.getId());
        }
        localDbModel.setRegionName(regionModel.getRegionName());
        localDbModel.setEducationName(regionModel.getEducationName());
        localDbModel.setDescription(regionModel.getDescription());
        localDbModel.setAdminUserId(regionModel.getAdminUserId());
        this.updateById(localDbModel);
    }
}
