package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.utils.TreeUtil;
import com.xzp.smartcampus.system.mapper.MenuMapper;
import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.vo.MenuTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单共有方法
 */
@Slf4j
public abstract class BaseMenuService extends IsolationBaseService<MenuMapper, MenuModel> {

    /**
     * 获取菜单类型
     *
     * @return String
     */
    protected abstract String getMenuType();

    /**
     * 查询菜单列表
     *
     * @param searchValue searchValue
     * @return List<MenuModel>
     */
    public List<MenuTreeVo> getMenuList(MenuModel searchValue) {
        // 搜索查询
        List<MenuModel> menuModels = this.selectList(new QueryWrapper<MenuModel>()
                .eq("app_type", this.getMenuType())
                .like(StringUtils.isNotBlank(searchValue.getMenuName()), "menu_name", searchValue.getMenuName())
                .like(StringUtils.isNotBlank(searchValue.getRoute()), "route", searchValue.getRoute())
                .like(StringUtils.isNotBlank(searchValue.getOperateName()), "operate_name", searchValue.getOperateName())
                .like(StringUtils.isNotBlank(searchValue.getOperateCode()), "operate_code", searchValue.getOperateCode())
                .orderByDesc("create_time")
        );
        if (CollectionUtils.isEmpty(menuModels)) {
            return Collections.emptyList();
        }
        // 查询需要展示的但是未搜索到的数据
        List<String> pids = this.getNotFindPids(menuModels);
        if (!CollectionUtils.isEmpty(pids)) {
            menuModels.addAll(this.selectByIds(pids));
        }
        // 转为树结构
        return TreeUtil.modelToTreeVo(menuModels, new MenuTreeVo());
    }

    /**
     * 查询需要展示的但是未搜索到的数据
     *
     * @param menuModels menuModels
     * @return List<String>
     */
    private List<String> getNotFindPids(List<MenuModel> menuModels) {
        if (CollectionUtils.isEmpty(menuModels)) {
            return Collections.emptyList();
        }
        List<String> treePaths = menuModels.stream().map(MenuModel::getTreePath).collect(Collectors.toList());
        List<String> ids = TreeUtil.treePathToIds(treePaths);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        Map<String, MenuModel> idToModelMap = menuModels.stream().collect(Collectors.toMap(MenuModel::getId, v -> v));
        return ids.stream().filter(id -> !idToModelMap.containsKey(id)).collect(Collectors.toList());
    }

    /**
     * 报错
     *
     * @param menuModel menuModel
     * @return MenuModel
     */
    public MenuModel saveMenuData(MenuModel menuModel) {
        // 新增
        if (StringUtils.isBlank(menuModel.getId())) {
            this.insertMenuModel(menuModel);
            return menuModel;
        }
        this.updateMenuModel(menuModel);
        return menuModel;
    }

    /**
     * 更新
     *
     * @param menuModel menuModel
     */
    private void updateMenuModel(MenuModel menuModel) {
        if (StringUtils.isBlank(menuModel.getId())) {
            log.error("update id is null");
            throw new SipException("数据错误，更新操作id不能为空");
        }
        // 更新
        MenuModel localDbModel = this.selectById(menuModel.getId());
        if (localDbModel == null) {
            log.error("data error,not find MenuModel by id {}", menuModel.getId());
            throw new SipException("数据错误，找不到MenuModel id为" + menuModel.getId());
        }
        localDbModel.setMenuName(menuModel.getMenuName());
        localDbModel.setRoute(menuModel.getRoute());
        localDbModel.setOperateName(menuModel.getOperateName());
        localDbModel.setOperateCode(menuModel.getOperateCode());
        localDbModel.setDescription(menuModel.getDescription());
        this.updateById(localDbModel);
    }

    /**
     * 新增数据
     *
     * @param menuModel menuModel
     */
    private void insertMenuModel(MenuModel menuModel) {
        menuModel.setAppType(this.getMenuType());
        menuModel.setId(SqlUtil.getUUId());
        if (StringUtils.isNotBlank(menuModel.getPid())) {
            MenuModel pMenuModel = this.selectById(menuModel.getPid());
            if (pMenuModel == null) {
                log.error("data error,not find MenuModel by id {}", menuModel.getPid());
                throw new SipException("数据错误，找不到MenuModel id为" + menuModel.getPid());
            }
            menuModel.setMenuLevel(pMenuModel.getMenuLevel() + 1);
            menuModel.setTreePath(pMenuModel.getTreePath() + Constant.TREE_SEPARATOR + menuModel.getId());
        } else {
            menuModel.setPid(Constant.ROOT);
            menuModel.setTreePath(Constant.ROOT + Constant.TREE_SEPARATOR + menuModel.getId());
            menuModel.setMenuLevel(1);
        }
        this.insert(menuModel);
    }
}
