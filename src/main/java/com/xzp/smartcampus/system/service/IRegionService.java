package com.xzp.smartcampus.system.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.vo.RegionVo;

/**
 * 教育局业务类
 */
public interface IRegionService extends IBaseService<RegionModel> {

    /**
     * 分页查询教育局数据
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return PageResult
     */
    PageResult getRegionListPage(RegionModel searchValue, Integer current, Integer pageSize);

    /**
     * 保存教育局数据
     *
     * @param regionVo regionModel
     * @return RegionModel
     */
    RegionModel postRegionModel(RegionVo regionVo);

    /**
     * 数据校验
     *
     * @param regionVo regionVo
     */
    void validatorRegion(RegionVo regionVo);

    /**
     * 创建管理员用户
     *
     * @param staffGroupModel 区域id
     * @param password        登录密码
     * @param contact         手机号
     * @return StaffModel
     */
    StaffModel createAdminUserGroup(StaffGroupModel staffGroupModel, String password, String contact);

    /**
     * 更新管理员用户信息
     *
     * @param userId              用户id
     * @param authorityTemplateId 权限模板id
     * @param password            密码
     * @param contact             手机号码
     */
    StaffModel updateAdminUser(String regionId, String schoolId, String userId, String authorityTemplateId, String password, String contact);

    /**
     * 创建管理
     *
     * @param regionId            regionId
     * @param schoolId            schoolId
     * @param authorityTemplateId authorityTemplateId
     * @param password            password
     * @param contact             contact
     * @return StaffModel
     */
    StaffModel createAdminUserGroup(String regionId, String schoolId, String authorityTemplateId, String password, String contact);
}
