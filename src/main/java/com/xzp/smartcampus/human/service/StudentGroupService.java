package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StudentGroupModel;

/**
 * 学生信息系统
 */
public interface StudentGroupService extends IBaseService<StudentGroupModel> {
    /* 新增一个组的信息，需要信息判断，不能直接 Insert */
    public StudentGroupModel addStudentGroup(StudentGroupModel studentGroup);

    /* 修改一个学生组信息，需要信息判断，并且把 Restful PATCH/PUT 合为一体 */
    public StudentGroupModel changeStudentGroup(StudentGroupModel studentGroup);



}
