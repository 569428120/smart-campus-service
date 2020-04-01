package com.xzp.smartcampus.human.service.impl;

import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.human.mapper.StaffContactMapper;
import com.xzp.smartcampus.human.model.StaffContactModel;
import com.xzp.smartcampus.human.service.StaffContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StaffContactServiceImpl
        extends IsolationBaseService<StaffContactMapper, StaffContactModel>
        implements StaffContactService {

    /**
     * 新增一个联系人信息
     * @param contact
     * @param sid  Staff的id
     */
    public StaffContactModel addContact(StaffContactModel contact, String sid) {

        return null;
    }

    /* 删除一个联系人信息 */
    public StaffContactModel delContact(StaffContactModel contact, String sid) {
        return null;
    }


    /* 修改一个联系人信息 */
    public StaffContactModel changeContact(StaffContactModel contact, String sid) {
        return null;
    }


    /* 查询学生相关的所有联系人的信息 */
    public List<StaffContactModel> getContactsByStaffId(String sid) {
        return null;
    }




}
