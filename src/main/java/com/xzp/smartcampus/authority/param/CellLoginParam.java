package com.xzp.smartcampus.authority.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 手机号码登录参数
 *
 * @author SGS
 */
@Data
public class CellLoginParam {

    @NotBlank(message = "手机号码不能为空")
    private String cellNumber;

    @NotBlank(message = "验证码不能为空")
    private String verificationCode;
}
