package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.mapper.AuthorityGroupMapper;
import com.xzp.smartcampus.system.model.AuthorityGroupModel;
import com.xzp.smartcampus.system.model.AuthorityGroupToMenuModel;
import com.xzp.smartcampus.system.service.IAuthorityGroupService;
import com.xzp.smartcampus.system.service.IAuthorityGroupToMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AuthorityGroupServiceImpl extends IsolationBaseService<AuthorityGroupMapper, AuthorityGroupModel> implements IAuthorityGroupService {

    @Resource
    private IAuthorityGroupToMenuService groupToMenuService;

    /**
     * 分页查询
     *
     * @param searchValue 查询条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return PageResult
     */
    @Override
    public PageResult getAuthorityGroupPage(AuthorityGroupModel searchValue, Integer current, Integer pageSize) {
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<AuthorityGroupModel>()
                .like(StringUtils.isNotBlank(searchValue.getAuthorityName()), "authority_name", searchValue.getAuthorityName())
                .like(StringUtils.isNotBlank(searchValue.getAuthorityCode()), "authority_code", searchValue.getAuthorityCode())
                .orderByDesc("create_time")
        );
    }

    /**
     * 保存权限组数据
     *
     * @param groupModel 数据
     * @return AuthorityGroupModel
     */
    @Override
    public AuthorityGroupModel saveAuthorityGroup(AuthorityGroupModel groupModel) {
        // 新增操作
        if (StringUtils.isBlank(groupModel.getId())) {
            groupModel.setId(SqlUtil.getUUId());
            this.insert(groupModel);
            return groupModel;
        }
        // 更新操作
        AuthorityGroupModel localDbGroupModel = this.selectById(groupModel.getId());
        if (localDbGroupModel == null) {
            log.error("not find AuthorityGroupModel by id {}", groupModel.getId());
            throw new SipException("数据错误，找不到AuthorityGroupModel id为 " + groupModel.getId());
        }
        localDbGroupModel.setAuthorityName(groupModel.getAuthorityName());
        localDbGroupModel.setAuthorityCode(groupModel.getAuthorityCode());
        localDbGroupModel.setDescription(groupModel.getDescription());
        this.updateById(localDbGroupModel);
        return localDbGroupModel;
    }

    /**
     * 删除权限组数据
     *
     * @param groupIds groupIds
     */
    @Override
    public void deleteAuthorityGroupByIds(List<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            log.info("groupIds is null");
            return;
        }
        groupToMenuService.delete(new UpdateWrapper<AuthorityGroupToMenuModel>()
                .in("group_id", groupIds)
        );
        this.deleteByIds(groupIds);
    }

    /**
     * 权限模板
     *
     * @param authorityTemplateId templateId
     * @param regionId            regionId
     * @param schoolId            schoolId
     */
    @Override
    public AuthorityGroupModel copyAuthorityGroupTemplate(String authorityTemplateId, String regionId, String schoolId) {
        if (StringUtils.isBlank(authorityTemplateId)) {
            log.warn("authorityTemplateId is null");
            throw new SipException("参数错误，authorityTemplateId 为空");
        }
        AuthorityGroupModel groupModel = this.selectById(authorityTemplateId);
        if (groupModel == null) {
            log.warn("not AuthorityGroupModel by id {}", authorityTemplateId);
            throw new SipException("参数错误，找不到AuthorityGroupModel authorityTemplateId为" + authorityTemplateId);
        }
        List<AuthorityGroupToMenuModel> menuModels = groupToMenuService.selectList(new QueryWrapper<AuthorityGroupToMenuModel>()
                .eq("group_id", groupModel.getId())
        );
        // 复制数据
        groupModel.setId(SqlUtil.getUUId());
        groupModel.setTemplate(1);
        groupModel.setRegionId(regionId);
        groupModel.setSchoolId(schoolId);
        if (!CollectionUtils.isEmpty(menuModels)) {
            menuModels.forEach(item -> {
                item.setId(SqlUtil.getUUId());
                item.setGroupId(groupModel.getId());
                item.setRegionId(regionId);
                item.setSchoolId(schoolId);
            });
            groupToMenuService.insertBatch(menuModels);
        }
        this.insert(groupModel);
        return groupModel;
    }
}
