package com.xzp.smartcampus.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.authority.mapper.RoleToMenuMapper;
import com.xzp.smartcampus.authority.model.RoleToMenuModel;
import com.xzp.smartcampus.authority.service.IRoleToMenuService;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.mobileapi.vo.AppInfo;
import com.xzp.smartcampus.system.enums.MenuType;
import com.xzp.smartcampus.system.mapper.MenuMapper;
import com.xzp.smartcampus.system.model.MenuModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
public class RoleToMenuServiceImpl extends IsolationBaseService<RoleToMenuMapper, RoleToMenuModel> implements IRoleToMenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 根据角色查询手机端菜单
     *
     * @param roleIds 角色id
     * @return List<AppInfo>
     */
    @Override
    public List<AppInfo> getAppInfoByRoleIds(List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            log.warn("roleIds is null");
            return Collections.emptyList();
        }
        List<RoleToMenuModel> roleToMenuModels = this.selectByIds(roleIds);
        if (CollectionUtils.isEmpty(roleToMenuModels)) {
            log.warn("not find roleToMenuModels by roleIds {}", roleIds);
            return Collections.emptyList();
        }
        List<String> menuIds = roleToMenuModels.stream().map(RoleToMenuModel::getMenuId).collect(Collectors.toList());
        // 分类
        List<MenuModel> groupModels = menuMapper.selectList(new QueryWrapper<MenuModel>()
                .eq("app_type", MenuType.MOBILE_MENU.getKey())
                .eq("menu_level", 1)
        );
        Map<String, MenuModel> groupIdToModelMap = CollectionUtils.isEmpty(groupModels) ?
                Collections.emptyMap() :
                groupModels.stream().collect(Collectors.toMap(MenuModel::getId, v -> v));
        // 菜单
        List<MenuModel> menuModels = menuMapper.selectList(new QueryWrapper<MenuModel>()
                .in("id", menuIds)
                .eq("app_type", MenuType.MOBILE_MENU.getKey())
        );
        if (CollectionUtils.isEmpty(menuModels)) {
            log.warn("not find menuModels by menuIds {}", menuIds);
            return Collections.emptyList();
        }
        return menuModels.stream().map(item -> AppInfo.newInstance(groupIdToModelMap.get(item.getPid()), item)).collect(Collectors.toList());
    }
}
