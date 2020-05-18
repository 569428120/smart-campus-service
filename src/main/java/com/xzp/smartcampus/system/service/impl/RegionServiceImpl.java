package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.NonIsolationBaseService;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.StaffMapper;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffGroupService;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.system.mapper.RegionMapper;
import com.xzp.smartcampus.system.model.AuthorityGroupModel;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.service.IAuthorityGroupService;
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
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RegionServiceImpl extends NonIsolationBaseService<RegionMapper, RegionModel> implements IRegionService {

    @Resource
    private IAuthorityGroupService authorityGroupService;

    @Resource
    private StaffMapper userMapper;

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
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<RegionModel>()
                .like(StringUtils.isNotBlank(searchValue.getRegionName()), "region_name", searchValue.getRegionName())
                .like(StringUtils.isNotBlank(searchValue.getEducationName()), "education_name", searchValue.getEducationName())
                .orderByDesc("create_time")
        );
    }

    /**
     * 保存教育局数据
     *
     * @param regionVo regionModel
     * @return RegionModel
     */
    @Override
    public RegionModel postRegionModel(RegionVo regionVo) {
        // 校验数据
        this.validatorRegion(regionVo);
        // 新增操作
        if (StringUtils.isBlank(regionVo.getId())) {
            regionVo.setId(SqlUtil.getUUId());
            AuthorityGroupModel authorityTemplate = this.createAuthorityTemplate(regionVo.getId(), regionVo.getAuthorityTemplateId());
            this.createAdminUserGroup(authorityTemplate, regionVo.getUserName(), regionVo.getPassword());
            this.insert(regionVo);
            return regionVo;
        }
        this.updateRegionModel(regionVo);
        return regionVo;
    }

    /**
     * 数据校验
     *
     * @param regionVo regionVo
     */
    @Override
    public void validatorRegion(RegionVo regionVo) {
        // 权限模板不能为空
        if (StringUtils.isBlank(regionVo.getAuthorityTemplateId())) {
            log.warn("authorityTemplateId is null");
            throw new SipException(MessageFormat.format("参数错误，authorityTemplateId {0} 为空", regionVo.getAuthorityTemplateId()));
        }
        // 管理员用户用户名不能存在（不做数据隔离）
        List<StaffModel> userList = userMapper.selectList(new QueryWrapper<StaffModel>()
                .eq("user_name", regionVo.getUserName())
                .notIn(StringUtils.isNotBlank(regionVo.getAdminUserId()), "id", regionVo.getAdminUserId())
        );
        if (!CollectionUtils.isEmpty(userList)) {
            log.warn("exist user {}", userList.get(0));
            throw new SipException(MessageFormat.format("{0} 用户已经存在", regionVo.getUserName()));
        }
    }

    /**
     * 创建管理员用户组
     *
     * @param authorityTemplate authorityTemplate
     * @param userName          登录用户名
     * @param password          密码
     */
    private void createAdminUserGroup(AuthorityGroupModel authorityTemplate, String userName, String password) {
        if (authorityTemplate == null || StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            throw new SipException("authorityTemplate or userName or password is null");
        }
        // 创建管理员用户组
        StaffGroupModel staffGroupModel = new StaffGroupModel();
        staffGroupModel.setId(SqlUtil.getUUId());
        staffGroupModel.setAuthorityId(authorityTemplate.getId());
        staffGroupModel.setGroupName("管理员");
        staffGroupModel.setGroupCode(staffGroupModel.getId());
        staffGroupModel.setPid(Constant.ROOT);
        staffGroupModel.setTreePath(Constant.ROOT + staffGroupModel.getId());
        staffGroupModel.setDescription("系统自动生成的管理员用户");
        staffGroupService.insert(staffGroupModel);

        StaffModel staffModel = new StaffModel();
        staffModel.setId(SqlUtil.getUUId());
        staffModel.setName("系统预制管理员");
        staffModel.setUserName(userName);
        staffModel.setUserPassword(password);
        userService.insert(staffModel);
    }

    /**
     * 保存权限模板
     */
    private AuthorityGroupModel createAuthorityTemplate(String regionId, String authorityTemplateId) {
        if (StringUtils.isBlank(regionId) || StringUtils.isBlank(authorityTemplateId)) {
            log.info("regionId or authorityTemplateId is null");
            throw new SipException("regionId or authorityTemplateId is null");
        }
        // 查询当前租户是否已经存在权限模板
        List<AuthorityGroupModel> authorityGroupModels = authorityGroupService.selectList(new QueryWrapper<AuthorityGroupModel>()
                .eq("template", true)
        );
        if (!CollectionUtils.isEmpty(authorityGroupModels)) {
            log.info("template is exist");
            return authorityGroupModels.get(0);
        }
        // 不存在权限模板则创建模板
        return authorityGroupService.copyAuthorityGroupTemplate(authorityTemplateId);
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
        this.updateById(localDbModel);
    }
}
