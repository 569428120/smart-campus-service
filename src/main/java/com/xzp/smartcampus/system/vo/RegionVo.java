package com.xzp.smartcampus.system.vo;

import com.xzp.smartcampus.system.model.RegionModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegionVo extends RegionModel {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;
}
