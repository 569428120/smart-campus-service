package com.xzp.smartcampus.human.service.impl;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.human.mapper.StudentContactMapper;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.StudentContactService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StudentContactServiceImpl
        extends IsolationBaseService<StudentContactMapper, StudentContactModel>
        implements StudentContactService {

    /**
     * 新增一个联系人信息
     * @param contact
     */
    public StudentContactModel addContact(StudentContactModel contact) {

        return null;
    }

    /* 删除一个联系人信息 */
    public StudentContactModel delContact(StudentContactModel contact) {
        return null;
    }


    /* 修改一个联系人信息 */
    public StudentContactModel changeContact(StudentContactModel contact) {
        return null;
    }


    /* 查询学生相关的所有联系人的信息 */
    public List<StudentContactModel> getContactsByStudentId(String sid) {
        return null;
    }




}
