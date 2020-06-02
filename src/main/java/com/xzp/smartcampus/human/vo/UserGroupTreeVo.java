package com.xzp.smartcampus.human.vo;

import com.xzp.smartcampus.common.vo.TreeVo;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class UserGroupTreeVo extends StaffGroupModel implements TreeVo<UserGroupTreeVo, StaffGroupModel> {

    private List<UserGroupTreeVo> children;

    /**
     * 获取子节点
     *
     * @return List<TreeVo>
     */
    @Override
    public List<UserGroupTreeVo> getChildren() {
        return children;
    }

    /**
     * 设置子节点
     *
     * @param treeVos treeVos
     */
    @Override
    public void setChildren(List<UserGroupTreeVo> treeVos) {
        this.children = treeVos;
    }

    /**
     * model转vo
     *
     * @param model model
     * @return T
     */
    @Override
    public UserGroupTreeVo modelToVo(StaffGroupModel model) {
        UserGroupTreeVo treeVo = new UserGroupTreeVo();
        BeanUtils.copyProperties(model, treeVo);
        return treeVo;
    }
}
