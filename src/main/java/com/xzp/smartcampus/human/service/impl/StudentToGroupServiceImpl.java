package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.human.mapper.StudentGroupMapper;
import com.xzp.smartcampus.human.mapper.StudentToGroupMapper;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.model.StudentToGroupModel;
import com.xzp.smartcampus.human.service.StudentGroupService;
import com.xzp.smartcampus.human.service.StudentService;
import com.xzp.smartcampus.human.service.StudentToGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StudentToGroupServiceImpl
        extends IsolationBaseService<StudentToGroupMapper, StudentToGroupModel>
        implements StudentToGroupService {

    /* 组 增加一个学生：检查学生/组的合法性，然后插入 */
    public boolean groupAddMenber(String gid, String id) {
        StudentService studentService = new StudentServiceImpl();
        StudentModel student = studentService.selectById(id);
        StudentGroupService groupService = new StudentGroupServiceImpl();
        StudentGroupModel group = groupService.selectById(gid);

        if (student == null){ throw new SipException("数据不合法：Student 不存在"); }
        if (group == null){ throw new SipException("数据不合法：Group 不存在"); }

        /* TODO 这样的逻辑也许是无效的：按照 id+gid 查询是否存在 */
        QueryWrapper<StudentToGroupModel> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper result = queryWrapper.lambda().eq(StudentToGroupModel::getStudentId, id).and(
                queryWrapper1 -> queryWrapper1.eq(StudentToGroupModel::getGroupId, gid)).select();
        if (result != null){
            StudentToGroupModel relation = new StudentToGroupModel();
            relation.setGroupId(gid);
            relation.setStudentId(id);
            this.insert(relation);
        }
        return true;
    }

    /* 组 删除一个学生：检查学生/组的合法性，然后删除 */
    public boolean groupDelMenber(String gid, String id){
        StudentService studentService = new StudentServiceImpl();
        StudentModel student = studentService.selectById(id);
        StudentGroupService groupService = new StudentGroupServiceImpl();
        StudentGroupModel group = groupService.selectById(gid);

        if (student == null){ throw new SipException("数据不合法：Student 不存在"); }
        if (group == null){ throw new SipException("数据不合法：Group 不存在"); }

        /* TODO 这样的逻辑也许是无效的：按照 id+gid 查询是否存在 */
        QueryWrapper<StudentToGroupModel> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper result = queryWrapper.lambda().eq(StudentToGroupModel::getStudentId, id).and(
                queryWrapper1 -> queryWrapper1.eq(StudentToGroupModel::getGroupId, gid)).select();
        if (result == null){
            throw new SipException("数据不合法：组内并不存在这个成员");
        }
        /* TODO 如何删除它？？？ */
//        this.delete(result);
        return true;
    }




}
