package com.xzp.smartcampus.human.service;


import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.vo.UserVo;

import java.util.List;

/**
 * 员工信息系统
 */
public interface IStaffUserService extends IBaseService<StaffModel> {

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页数量
     * @return PageResult<StaffModel>
     */
    PageResult<UserVo> getUserVoListPage(StaffModel searchValue, Integer current, Integer pageSize);

    /**
     * 保存数据
     *
     * @param staffModel 数据
     * @return StaffModel
     */
    StaffModel postStaffUserModel(StaffModel staffModel);

    /**
     * 根据id获取用户vo
     *
     * @param userIds userIds
     * @return List<UserVo>
     */
    List<UserVo> getUserVoListByIds(List<String> userIds);

    /**
     * 转换为vo对象
     *
     * @param data data
     * @return List<UserVo>
     */
    List<UserVo> toUserVoLList(List<StaffModel> data);
}
