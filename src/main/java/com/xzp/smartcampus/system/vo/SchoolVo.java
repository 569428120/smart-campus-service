package com.xzp.smartcampus.system.vo;

import com.xzp.smartcampus.system.model.SchoolModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SchoolVo extends SchoolModel {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String contact;

    /**
     * 教育局名称
     */
    private String regionName;

}
