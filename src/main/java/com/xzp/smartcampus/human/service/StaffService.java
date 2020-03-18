package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StaffModel;

/**
 * 员工信息系统
 */
public interface StaffService extends IBaseService<StaffModel> {
    /* 新增一个学生的信息，需要信息判断，不能直接 Insert */
    public StaffModel addStaff(StaffModel staff);

    /* 修改一个学生的信息，需要信息判断，并且把 Restful PATCH/PUT 合为一体 */
    public StaffModel changeStaff(StaffModel staff);

}
