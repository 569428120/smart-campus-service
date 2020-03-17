package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StudentModel;

/**
 * 学生信息系统
 */
public interface StudentService extends IBaseService<StudentModel> {
    /* 新增一个学生的信息，需要信息判断，不能直接 Insert */
    public StudentModel addStudent(StudentModel student);

    /* 修改一个学生的信息，需要信息判断，并且把 Restful PATCH/PUT 合为一体 */
    public StudentModel changeStudent(StudentModel student);

}
