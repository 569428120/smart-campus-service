package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.StaffMapper;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StaffServiceImpl extends IsolationBaseService<StaffMapper, StaffModel> implements IStaffUserService {


    @Override
    public PageResult<StaffModel> getStaffListPage(StaffModel searchValue, Integer current, Integer pageSize) {
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<StaffModel>()
                .eq(StringUtils.isNotBlank(searchValue.getGroupId()), "group_id", searchValue.getGroupId())
                .eq(StringUtils.isNotBlank(searchValue.getUserType()), "user_type", searchValue.getUserType())
                .eq(StringUtils.isNotBlank(searchValue.getUserIdentity()), "user_identity", searchValue.getUserIdentity())
                .eq(StringUtils.isNotBlank(searchValue.getUserIdentity()), "user_job_code", searchValue.getUserIdentity())
                .like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName())
                .orderByDesc("create_time"));
    }

    /**
     * 保存数据
     *
     * @param staffModel 数据
     * @return StaffModel
     */
    @Override
    public StaffModel postStaffUserModel(StaffModel staffModel) {
        if (StringUtils.isBlank(staffModel.getId())) {
            return this.insertStaffUserModel(staffModel);
        }
        return this.updateStaffUserModel(staffModel);
    }

    /**
     * 更新用户 不能更新分组
     *
     * @param staffModel staffModel
     * @return StaffModel
     */
    private StaffModel updateStaffUserModel(StaffModel staffModel) {
        if (StringUtils.isBlank(staffModel.getId())) {
            log.warn("id is null");
            throw new SipException("数据错误，id不能为空");
        }
        StaffModel localModel = this.selectById(staffModel.getId());
        if (localModel == null) {
            log.warn("not find localModel by id {}", staffModel.getId());
            throw new SipException("参数错误，找不到需要更新的数据 id " + staffModel.getId());
        }
        localModel.setContact(staffModel.getContact());
        localModel.setUserPassword(staffModel.getUserPassword());
        localModel.setName(staffModel.getName());
        localModel.setUserIdentity(staffModel.getUserIdentity());
        localModel.setUserType(staffModel.getUserType());
        localModel.setAddress(staffModel.getAddress());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 新增数据
     *
     * @param staffModel 数据
     */
    private StaffModel insertStaffUserModel(StaffModel staffModel) {
        if (StringUtils.isBlank(staffModel.getGroupId())) {
            log.warn("groupId is null");
            throw new SipException("参数错误，groupId不能为空");
        }
        staffModel.setId(SqlUtil.getUUId());
        this.insert(staffModel);
        return staffModel;
    }
}
