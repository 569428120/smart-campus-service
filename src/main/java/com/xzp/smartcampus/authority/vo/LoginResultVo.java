package com.xzp.smartcampus.authority.vo;

import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 登录用户数据
 *
 * @author SGS
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResultVo extends LoginUserInfo {

    /**
     * userModel的id
     */
    private String bindUserId;

    /**
     * 认证吗
     */
    private String authentication;

    /**
     * 角色列表
     */
    private List<LoginUserToRoleVo> roleVos;
}
