package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.human.mapper.StaffToGroupMapper;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.model.StaffToGroupModel;
import com.xzp.smartcampus.human.service.IStaffGroupService;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.human.service.StaffToGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 组的成员管理
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StaffToGroupServiceImpl
        extends IsolationBaseService<StaffToGroupMapper, StaffToGroupModel>
        implements StaffToGroupService {

    @Resource
    private IStaffUserService StaffService;
    @Resource
    private IStaffGroupService groupService;

    /**
     * 组 增加一个成员：检查成员/组的合法性、是否与原数据冲突，然后插入
     * @param gid  组的 id
     * @param id  组内成员的 id
     */
    public boolean putMenberIntogroup(String gid, String id) {
        StaffModel Staff = StaffService.selectById(id);
        StaffGroupModel group = groupService.selectById(gid);
        // 如果 Staff/group 不存在，那么说明输入数据有误
        if (Staff == null){ throw new SipException("数据不合法：Staff 不存在"); }
        if (group == null){ throw new SipException("数据不合法：Group 不存在"); }
        // 按照 id+gid 查询 关联关系 是否存在
        List<StaffToGroupModel> StaffToGroupList = this.selectList(
            new QueryWrapper<StaffToGroupModel>().eq("StaffId", id).eq("roupId", gid)
        );
        // 查询结果为空，说明 这个成员 还不在这个组，就 建立关联-> 拉入组
        if (StaffToGroupList.isEmpty()){
            StaffToGroupModel relation = new StaffToGroupModel();
            relation.setGroupId(gid);
            relation.setUserId(id);
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
        StaffModel Staff = StaffService.selectById(id);
        StaffGroupModel group = groupService.selectById(gid);
        // 如果 Staff/group 不存在，那么说明输入数据有误
        if (Staff == null){ throw new SipException("数据不合法：Staff 不存在"); }
        if (group == null){ throw new SipException("数据不合法：Group 不存在"); }
        // 按照 id+gid 查询 关联关系 是否存在
        List<StaffToGroupModel> StaffToGroupList = this.selectList(
          new QueryWrapper<StaffToGroupModel>().eq("StaffId", id).eq("roupId", gid)
        );
        // 查询结果为空，说明 这个成员 还不在这个组，输入的数据有误
        if (StaffToGroupList.isEmpty()){ throw new SipException("数据不合法：组内并不存在这个成员"); }
        // 删除，完事儿
        this.deleteById(StaffToGroupList.get(0).getId());

        return true;
    }




}
