package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StudentContactModel;

import java.util.List;


/**
 * 学生的联系人管理
 */
public interface IStudentContactService extends IBaseService<StudentContactModel> {

    /**
     * 保存联系人
     *
     * @param contactList 联系人列表
     * @param studentId   学生id
     */
    void saveStudentContact(List<StudentContactModel> contactList, String studentId);
}
