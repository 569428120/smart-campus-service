package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.StudentToGroupModel;

/**
 * 学生信息系统
 */
public interface StudentToGroupService extends IBaseService<StudentToGroupModel> {
    /* 组 增加一个学生：检查学生/组的合法性，然后插入 */
    public boolean putMenberIntogroup(String gid, String id);

    /* 组 删除一个学生：检查学生/组的合法性，然后删除 */
    public boolean removeMenberOutgroup(String gid, String id);

}
