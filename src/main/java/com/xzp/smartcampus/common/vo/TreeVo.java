package com.xzp.smartcampus.common.vo;

import com.xzp.smartcampus.common.model.BaseModel;

import java.util.List;

/**
 * 树结构
 */
public interface TreeVo<T extends TreeVo, M extends BaseModel> {

    /**
     * 获取id
     *
     * @return String
     */
    String getId();

    /**
     * pid
     *
     * @return String
     */
    String getPid();

    /**
     * 获取子节点
     *
     * @return List<TreeVo>
     */
    List<T> getChild();

    /**
     * 设置子节点
     *
     * @param treeVos treeVos
     */
    void setChild(List<T> treeVos);

    /**
     * model转vo
     *
     * @param model model
     * @return T
     */
    T modelToVo(M model);
}
