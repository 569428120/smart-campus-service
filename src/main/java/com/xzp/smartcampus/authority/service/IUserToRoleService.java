package com.xzp.smartcampus.authority.service;

import com.xzp.smartcampus.authority.model.UserToRoleModel;
import com.xzp.smartcampus.authority.vo.LoginUserToRoleVo;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import com.xzp.smartcampus.system.model.SchoolModel;
import com.xzp.smartcampus.mobileapi.vo.AppInfo;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author SGS
 */
public interface IUserToRoleService extends IBaseService<UserToRoleModel> {

    /**
     * 获得角色列表，根据bindUserId
     *
     * @param cellNumber 手机号码
     * @return loginResultVo
     */
    List<LoginUserToRoleVo> getRolesByCellNumber(String cellNumber);

    /**
     * 学校
     *
     * @param schoolIds schoolIds
     * @return 映射
     */
    Map<String, SchoolModel> getIdToSchoolModelMap(List<String> schoolIds);

    /**
     * 根据用户id查询app列表
     *
     * @param userInfo 用户id
     * @return List<AppInfo>
     */
    List<AppInfo> getAppInfosByUserId(LoginUserInfo userInfo);
}
