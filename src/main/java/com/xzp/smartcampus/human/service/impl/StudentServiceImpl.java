package com.xzp.smartcampus.human.service.impl;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.human.mapper.StudentMapper;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.object.SqlCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StudentServiceImpl
        extends IsolationBaseService<StudentMapper, StudentModel>
        implements StudentService {

    /*新增一个学生信息。id 不能重复。*/
    @Override
    public StudentModel addStudent(StudentModel student){
        // 数据的 id 不能重复。
        String sid = student.getId();
        if (! StringUtils.isBlank(sid)){
            StudentModel old_student = this.selectById(sid);
            if (old_student != null) {
                throw new SipException("该学生数据已经存在：" + sid);
            }
        }
        // 学号能重复吗 ？等以后实际业务确定
        student.setId(SqlUtil.getUUId());
        this.insert(student);
        return student;
    }

    /* 修改一个学生的信息，需要信息判断，并且把 Restful PATCH/PUT 合为一体 */
    public StudentModel changeStudent(StudentModel student){
        String sid = student.getId();
        if (StringUtils.isBlank(sid)){
            throw new SipException("数据不合法：必须指定 id 才能修改数据");
        }
        StudentModel old_student = this.selectById(sid);
        if (old_student == null) {
            throw new SipException("该学生数据不存在：" + sid);
        }
        this.updateById(old_student);
        return old_student;
    }








}
