package com.xzp.smartcampus.authority.service;

import com.xzp.smartcampus.authority.model.RoleToMenuModel;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.mobileapi.vo.AppInfo;

import java.util.List;

/**
 * @author SGS
 */
public interface IRoleToMenuService extends IBaseService<RoleToMenuModel> {


    /**
     * 根据角色查询手机端菜单
     *
     * @param roleIds 角色id
     * @return List<AppInfo>
     */
    List<AppInfo> getAppInfoByRoleIds(List<String> roleIds);

}
