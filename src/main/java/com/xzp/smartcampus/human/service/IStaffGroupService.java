package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.vo.UserGroupTreeVo;

import java.util.List;

/**
 * 学生信息系统
 */
public interface IStaffGroupService extends IBaseService<StaffGroupModel> {

    /**
     * 查询树节点
     *
     * @param searchValue searchValue
     * @return List<UserGroupTreeVo>
     */
    List<UserGroupTreeVo> getUserGroupTreeVoList(StaffGroupModel searchValue);

    /**
     * 保存
     *
     * @param groupModel groupModel
     * @return StaffGroupModel
     */
    StaffGroupModel postGroupModel(StaffGroupModel groupModel);


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

    /**
     * 删除分组
     *
     * @param groupIds groupIds
     */
    void deleteGroupByIds(List<String> groupIds);

    /**
     * 获取父节点
     *
     * @param groupModels groupModels
     * @return List<String>
     */
    List<String> getNotFindPids(List<StaffGroupModel> groupModels);
}
