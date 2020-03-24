package com.xzp.smartcampus.system.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.system.model.AuthorityGroupToMenuModel;
import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.vo.MenuTreeVo;

import java.util.List;

/**
 * 权限组对应的菜单
 */
public interface IAuthorityGroupToMenuService extends IBaseService<AuthorityGroupToMenuModel> {

    /**
     * 获取权限组下的菜单列表
     *
     * @param groupId 权限组id
     * @return List<MenuModel>
     */
    List<MenuTreeVo> getMenuListByGroupId(String groupId);

    /**
     * 将权限组和菜单建立关系
     *
     * @param groupId groupId
     * @param menuIds menuIds
     */
    void postGroupToMenusByIds(String groupId, List<String> menuIds);
}
