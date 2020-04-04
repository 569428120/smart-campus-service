package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StaffContactModel;

import java.util.List;


/**
 * 学生的联系人管理
 */
public interface StaffContactService extends IBaseService<StaffContactModel> {
    /* 新增一个联系人信息 */
    public StaffContactModel addContact(StaffContactModel contact, String sid);

    /* 删除一个联系人信息 */
    public StaffContactModel delContact(StaffContactModel contact, String sid);

    /* 修改一个联系人信息 */
    public StaffContactModel changeContact(StaffContactModel contact, String sid);

    /* 查询职工相关的所有联系人的信息 */
    public List<StaffContactModel> getContactsByStaffId(String sid);
}
