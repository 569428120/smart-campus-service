package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StudentContactModel;

import java.util.List;


/**
 * 学生的联系人管理
 */
public interface StudentContactService extends IBaseService<StudentContactModel> {
    /* 新增一个联系人信息 */
    public StudentContactModel addContact(StudentContactModel contact);

    /* 删除一个联系人信息 */
    public StudentContactModel delContact(StudentContactModel contact);

    /* 修改一个联系人信息 */
    public StudentContactModel changeContact(StudentContactModel contact);

    /* 查询学生相关的所有联系人的信息 */
    public List<StudentContactModel> getContactsByStudentId(String sid);

}
