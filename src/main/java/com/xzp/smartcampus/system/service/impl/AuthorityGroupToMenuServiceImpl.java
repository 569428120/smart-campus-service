package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.TreeUtil;
import com.xzp.smartcampus.system.mapper.AuthorityGroupToMenuMapper;
import com.xzp.smartcampus.system.model.AuthorityGroupToMenuModel;
import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.service.IAuthorityGroupToMenuService;
import com.xzp.smartcampus.system.service.IPcMenuService;
import com.xzp.smartcampus.system.vo.MenuTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AuthorityGroupToMenuServiceImpl extends IsolationBaseService<AuthorityGroupToMenuMapper, AuthorityGroupToMenuModel> implements IAuthorityGroupToMenuService {

    @Resource
    private IPcMenuService pcMenuService;

    /**
     * 获取权限组下的菜单列表
     *
     * @param groupId 权限组id
     * @return List<MenuModel>
     */
    @Override
    public List<MenuTreeVo> getMenuListByGroupId(String groupId) {
        if (StringUtils.isBlank(groupId)) {
            log.info("groupId is null");
            return Collections.emptyList();
        }
        List<AuthorityGroupToMenuModel> groupToMenuModels = this.selectList(new QueryWrapper<AuthorityGroupToMenuModel>()
                .eq("group_id", groupId)
        );
        if (CollectionUtils.isEmpty(groupToMenuModels)) {
            log.info("not find groupToMenuModels by group_id {}", groupId);
            return Collections.emptyList();
        }
        // 查询MenuModel
        List<String> menuIds = groupToMenuModels.stream().map(AuthorityGroupToMenuModel::getMenuId).collect(Collectors.toList());
        List<MenuModel> menuModels = pcMenuService.selectByIds(menuIds);
        if (CollectionUtils.isEmpty(menuModels)) {
            log.info("not find menuModels by menuIds {}", menuIds);
            return Collections.emptyList();
        }
        return TreeUtil.modelToTreeVo(menuModels, new MenuTreeVo());
    }

    /**
     * 将权限组和菜单建立关系
     *
     * @param groupId groupId
     * @param menuIds menuIds
     */
    @Override
    public void postGroupToMenusByIds(String groupId, List<String> menuIds) {
        if (StringUtils.isBlank(groupId) || CollectionUtils.isEmpty(menuIds)) {
            log.warn("groupId or menuIds is null");
            return;
        }
        // 筛选出那些需要新增，那些需要删除，无需更新
        List<AuthorityGroupToMenuModel> groupToMenuModels = this.selectList(new QueryWrapper<AuthorityGroupToMenuModel>()
                .eq("group_id", groupId)
                .in("menu_id", menuIds)
        );
        List<String> localDbMenuIds = groupToMenuModels.stream().map(AuthorityGroupToMenuModel::getMenuId).collect(Collectors.toList());
        List<String> insertIds = new ArrayList<>();
        List<String> deleteIds = new ArrayList<>();
        groupToMenuModels.forEach(item -> {
            // 本地存在，传入的参数没有为删除
            if (localDbMenuIds.contains(item.getMenuId()) && !menuIds.contains(item.getMenuId())) {

            }
            // 本地不存在，传入有，为新增
            // 例外情况，报错处理

        });
    }


}
