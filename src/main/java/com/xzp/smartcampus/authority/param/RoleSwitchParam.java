package com.xzp.smartcampus.authority.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author SGS
 */
@Data
public class RoleSwitchParam {

    @NotBlank(message = "partId 不能为空")
    private String partId;
}
