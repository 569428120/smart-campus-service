package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.system.mapper.MenuMapper;
import com.xzp.smartcampus.system.model.MenuModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 菜单共有方法
 */
@Slf4j
public class BaseMenuService extends IsolationBaseService<MenuMapper, MenuModel> {

    /**
     * 查询菜单列表
     *
     * @param searchValue searchValue
     * @return List<MenuModel>
     */
    public List<MenuModel> getMenuList(MenuModel searchValue) {
        // 搜索查询
        List<MenuModel> menuModels = this.selectList(new QueryWrapper<MenuModel>()
                .like(StringUtils.isNotBlank(searchValue.getMenuName()), "menu_name", searchValue.getMenuName())
                .like(StringUtils.isNotBlank(searchValue.getRoute()), "route", searchValue.getRoute())
                .like(StringUtils.isNotBlank(searchValue.getOperateName()), "searchValue", searchValue.getOperateName())
                .like(StringUtils.isNotBlank(searchValue.getOperateCode()), "operate_code", searchValue.getOperateCode())
        );
        // 查询需要展示的但是未搜索到的数据

        return menuModels;
    }

    /**
     * 报错
     *
     * @param menuModel menuModel
     * @return MenuModel
     */
    public MenuModel saveMenuData(MenuModel menuModel) {
        return null;
    }
}
