package com.xzp.smartcampus.human.service.impl;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.human.mapper.StudentGroupMapper;
import com.xzp.smartcampus.human.mapper.StudentMapper;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.model.StudentToGroupModel;
import com.xzp.smartcampus.human.service.StudentGroupService;
import com.xzp.smartcampus.human.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StudentGroupServiceImpl
        extends IsolationBaseService<StudentGroupMapper, StudentGroupModel>
        implements StudentGroupService {

    @Override
    /* 新增一个组的信息，需要信息判断，不能直接 Insert */
    public StudentGroupModel addStudentGroup(StudentGroupModel group){
        // 数据的 id 不能重复。
        String id = group.getId();
        if (! StringUtils.isBlank(id)){
            StudentGroupModel origin = this.selectById(id);
            if (origin != null) {
                throw new SipException("该组的数据已经存在：" + id);
            }
        }
        // 学号能重复吗 ？等以后实际业务确定
        group.setId(SqlUtil.getUUId());
        this.insert(group);
        return group;
    }

    /* 修改一个学生组信息，需要信息判断，并且把 Restful PATCH/PUT 合为一体 */
    public StudentGroupModel changeStudentGroup(StudentGroupModel group){
        String id = group.getId();
        if (StringUtils.isBlank(id)){
            throw new SipException("数据不合法：必须指定 id 才能修改数据");
        }
        StudentGroupModel origin = this.selectById(id);
        if (origin == null) {
            throw new SipException("该组数据不存在：" + id);
        }
        this.updateById(origin);
        return origin;
    }





}
