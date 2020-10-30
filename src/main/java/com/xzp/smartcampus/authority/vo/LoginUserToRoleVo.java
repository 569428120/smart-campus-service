package com.xzp.smartcampus.authority.vo;

import lombok.Data;

import java.util.List;

/**
 * @author SGS
 */
@Data
public class LoginUserToRoleVo {
    private String userId;
    private String userType;
    private String name;
    private List<LoginUserToRoleVo> children;
}
