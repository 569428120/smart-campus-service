package com.xzp.smartcampus.system.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.system.model.MenuModel;

import java.util.List;

public interface IPcMenuService extends IBaseService<MenuModel> {

    /**
     * 查询菜单列表
     *
     * @param searchValue searchValue
     * @return List<MenuModel>
     */
    List<MenuModel> getMenuList(MenuModel searchValue);

    /**
     * 报错
     *
     * @param menuModel menuModel
     * @return MenuModel
     */
    MenuModel saveMenuData(MenuModel menuModel);
}
