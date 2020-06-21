package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.human.mapper.StudentContactMapper;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.service.IStudentContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StudentContactServiceImpl extends IsolationBaseService<StudentContactMapper, StudentContactModel> implements IStudentContactService {

    /**
     * 保存联系人
     *
     * @param contactList 联系人列表
     * @param studentId   学生id
     */
    @Override
    public void saveStudentContact(List<StudentContactModel> contactList, String studentId) {
        if (CollectionUtils.isEmpty(contactList) || StringUtils.isEmpty(studentId)) {
            log.warn("contactList or studentId is null");
            return;
        }
        this.delete(new UpdateWrapper<StudentContactModel>()
                .eq("student_id", studentId)
        );
        contactList.forEach(item -> {
            item.setId(SqlUtil.getUUId());
            item.setStudentId(studentId);
        });
        this.insertBatch(contactList);
    }
}
