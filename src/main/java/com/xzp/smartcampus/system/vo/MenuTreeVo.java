package com.xzp.smartcampus.system.vo;

import com.xzp.smartcampus.common.vo.TreeVo;
import com.xzp.smartcampus.system.model.MenuModel;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 菜单树节点
 */
public class MenuTreeVo extends MenuModel implements TreeVo<MenuTreeVo, MenuModel> {

    private List<MenuTreeVo> child;


    /**
     * 获取子节点
     *
     * @return List<TreeVo>
     */
    @Override
    public List<MenuTreeVo> getChild() {
        return child;
    }

    /**
     * 设置子节点
     *
     * @param treeVos treeVos
     */
    @Override
    public void setChild(List<MenuTreeVo> treeVos) {
        this.child = treeVos;
    }

    /**
     * model转vo
     *
     * @param model model
     * @return T
     */
    @Override
    public MenuTreeVo modelToVo(MenuModel model) {
        MenuTreeVo menuTreeVo = new MenuTreeVo();
        BeanUtils.copyProperties(model, menuTreeVo);
        return menuTreeVo;
    }
}
