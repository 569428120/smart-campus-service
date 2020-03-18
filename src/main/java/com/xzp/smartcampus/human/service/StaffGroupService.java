package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StaffGroupModel;

/**
 * 学生信息系统
 */
public interface StaffGroupService extends IBaseService<StaffGroupModel> {
    /* 新增一个组的信息，需要信息判断，不能直接 Insert */
    public StaffGroupModel addStaffGroup(StaffGroupModel StaffGroup);

    /* 修改一个学生组信息，需要信息判断，并且把 Restful PATCH/PUT 合为一体 */
    public StaffGroupModel changeStaffGroup(StaffGroupModel StaffGroup);



}
