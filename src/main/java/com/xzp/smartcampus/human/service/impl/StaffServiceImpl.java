package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.StaffMapper;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StaffServiceImpl
        extends IsolationBaseService<StaffMapper, StaffModel>
        implements StaffService {

    /*新增一个员工信息。id 不能重复。*/
    @Override
    public StaffModel addStaff(StaffModel Staff){
        // 数据的 id 不能重复。
        String sid = Staff.getId();
        if (! StringUtils.isBlank(sid)){
            StaffModel origin = this.selectById(sid);
            if (origin != null) {
                throw new SipException("该数据已经存在：" + sid);
            }
        }
        // 学号能重复吗 ？等以后实际业务确定
        Staff.setId(SqlUtil.getUUId());
        this.insert(Staff);
        return Staff;
    }

    /* 修改一个学生的信息，需要信息判断，并且把 Restful PATCH/PUT 合为一体 */
    public StaffModel changeStaff(StaffModel Staff){
        String sid = Staff.getId();
        if (StringUtils.isBlank(sid)){
            throw new SipException("数据不合法：必须指定 id 才能修改数据");
        }
        StaffModel origin = this.selectById(sid);
        if (origin == null) {
            throw new SipException("该数据不存在：" + sid);
        }
        this.updateById(origin);
        return origin;
    }

    @Override
    public PageResult getStaffListPage(StaffModel searchValue, Integer current, Integer pageSize) {
        PageResult pageResult = this.selectPage(new Page<>(current, pageSize), new QueryWrapper<StaffModel>()
                .eq(StringUtils.isNotBlank(searchValue.getId()), "id", searchValue.getId())
                .like(StringUtils.isNotBlank(searchValue.getName()), "Staff_name", searchValue.getName())
                .orderByDesc("create_time"));
        return pageResult;
    }






}
