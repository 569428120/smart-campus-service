package com.xzp.smartcampus.human.service.impl;

import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.human.mapper.StudentToGroupMapper;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.model.StudentToGroupModel;
import com.xzp.smartcampus.human.service.StudentGroupService;
import com.xzp.smartcampus.human.service.StudentService;
import com.xzp.smartcampus.human.service.StudentToGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 组的成员管理
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StudentToGroupServiceImpl
        extends IsolationBaseService<StudentToGroupMapper, StudentToGroupModel>
        implements StudentToGroupService {

    @Resource
    private StudentService studentService;
    @Resource
    private StudentGroupService groupService;

    /**
     * 组 增加一个成员：检查成员/组的合法性、是否与原数据冲突，然后插入
     * @param gid  组的 id
     * @param id  组内成员的 id
     */
    public boolean putMenberIntogroup(String gid, String id) {
        StudentModel student = studentService.selectById(id);
        StudentGroupModel group = groupService.selectById(gid);
        // 如果 student/group 不存在，那么说明输入数据有误
        if (student == null){ throw new SipException("数据不合法：Student 不存在"); }
        if (group == null){ throw new SipException("数据不合法：Group 不存在"); }
        // 按照 id+gid 查询 关联关系 是否存在
        List<StudentToGroupModel> studentToGroupList = this.selectList(
            new QueryWrapper<StudentToGroupModel>().eq("studentId", id).eq("roupId", gid)
        );
        // 查询结果为空，说明 这个成员 还不在这个组，就 建立关联-> 拉入组
        if (studentToGroupList.isEmpty()){
            StudentToGroupModel relation = new StudentToGroupModel();
            relation.setGroupId(gid);
            relation.setStudentId(id);
            this.insert(relation);
        }
        return true;
    }

    /**
     * 组 删除一个成员：检查成员/组的合法性、然后删除
     * @param gid  组的 id
     * @param id  组内成员的 id
     */
    public boolean removeMenberOutgroup(String gid, String id){
        StudentModel student = studentService.selectById(id);
        StudentGroupModel group = groupService.selectById(gid);
        // 如果 student/group 不存在，那么说明输入数据有误
        if (student == null){ throw new SipException("数据不合法：Student 不存在"); }
        if (group == null){ throw new SipException("数据不合法：Group 不存在"); }
        // 按照 id+gid 查询 关联关系 是否存在
        List<StudentToGroupModel> studentToGroupList = this.selectList(
          new QueryWrapper<StudentToGroupModel>().eq("studentId", id).eq("roupId", gid)
        );
        // 查询结果为空，说明 这个成员 还不在这个组，输入的数据有误
        if (studentToGroupList.isEmpty()){ throw new SipException("数据不合法：组内并不存在这个成员"); }
        // 删除，完事儿
        this.deleteById(studentToGroupList.get(0).getId());

        return true;
    }




}
