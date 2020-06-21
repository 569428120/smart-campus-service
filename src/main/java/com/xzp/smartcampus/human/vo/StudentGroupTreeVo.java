package com.xzp.smartcampus.human.vo;

import com.xzp.smartcampus.common.vo.TreeVo;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class StudentGroupTreeVo extends StudentGroupModel implements TreeVo<StudentGroupTreeVo, StudentGroupModel> {

    private List<StudentGroupTreeVo> children;

    /**
     * 获取子节点
     *
     * @return List<TreeVo>
     */
    @Override
    public List<StudentGroupTreeVo> getChildren() {
        return children;
    }

    /**
     * 设置子节点
     *
     * @param treeVos treeVos
     */
    @Override
    public void setChildren(List<StudentGroupTreeVo> treeVos) {
        children = treeVos;
    }

    /**
     * model转vo
     *
     * @param model model
     * @return T
     */
    @Override
    public StudentGroupTreeVo modelToVo(StudentGroupModel model) {
        StudentGroupTreeVo treeVo = new StudentGroupTreeVo();
        BeanUtils.copyProperties(model, treeVo);
        return treeVo;
    }
}
