package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.vo.StudentVo;
import com.xzp.smartcampus.human.vo.UserVo;

import java.util.List;

/**
 * 学生信息系统
 */
public interface IStudentService extends IBaseService<StudentModel> {

    /**
     * 分页查询
     *
     * @param searchValue searchValue
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<StudentVo>
     */
    PageResult<StudentVo> getStudentVoListPage(StudentModel searchValue, Integer current, Integer pageSize);

    /**
     * 保存数据
     *
     * @param studentModel studentModel
     * @return StudentModel
     */
    StudentModel postStudentModel(StudentModel studentModel);

    /**
     * 校验数据
     *
     * @param studentModel studentModel
     */
    void validatorStudentModel(StudentModel studentModel);

    /**
     * 获取学生vo
     *
     * @param studentIds studentIds
     * @return List<StudentVo>
     */
    List<StudentVo> getStudentVoListByIds(List<String> studentIds);

    /**
     * 分页查询，转换为UserVo对象
     *
     * @param searchValue searchValue
     * @param groupId     groupId
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<UserVo>
     */
    PageResult<UserVo> getUserVoPage(UserVo searchValue, String groupId, Integer current, Integer pageSize);

    /**
     * 获得家长列表
     *
     * @param searchValue searchValue
     * @param groupId     groupId
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<UserVo>
     */
    PageResult<UserVo> getParentUserVoPage(UserVo searchValue, String groupId, Integer current, Integer pageSize);

    /**
     * 根据用户id获得家长列表
     *
     * @param userIds userIds
     * @return List<UserVo>
     */
    List<UserVo> getParentUserVoListByIds(List<String> userIds);
}
