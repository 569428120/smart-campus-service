package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.vo.StudentGroupTreeVo;

import java.util.List;

/**
 * 学生信息系统
 */
public interface IStudentGroupService extends IBaseService<StudentGroupModel> {

    /**
     * 查询分组树
     *
     * @param searchValue 搜索条件
     * @return List<StudentGroupTreeVo>
     */
    List<StudentGroupTreeVo> getStudentGroupVoTreeList(StudentGroupModel searchValue);

    /**
     * 删除学生分组
     *
     * @param groupIds 分组id
     */
    void deleteGroupByIds(List<String> groupIds);

    /**
     * 保存数据
     *
     * @param groupModel groupModel
     * @return StudentGroupModel
     */
    StudentGroupModel postGroupModel(StudentGroupModel groupModel);

    /**
     * 复制分组
     *
     * @param sourceIds sourceIds
     * @param targetIds targetIds
     */
    void copyGroupToGroups(List<String> sourceIds, List<String> targetIds);

    /**
     * 移动分组
     *
     * @param sourceIds sourceIds
     * @param targetId  targetId
     */
    void moveGroupToGroups(List<String> sourceIds, String targetId);

    /**
     * 移动用户到分组
     *
     * @param userIds  userIds
     * @param targetId targetId
     */
    void moveUserToGroups(List<String> userIds, String targetId);
}
