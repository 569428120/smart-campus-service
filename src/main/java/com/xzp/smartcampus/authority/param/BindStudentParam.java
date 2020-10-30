package com.xzp.smartcampus.authority.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author SGS
 */
@Data
public class BindStudentParam {

    @NotBlank(message = "学生卡号不能为空")
    private String cardNumber;

    @NotBlank(message = "学生名称不能为空")
    private String studentName;

    @NotBlank(message = "手机号码不能为空")
    private String cellNumber;
}
