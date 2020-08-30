package com.xzp.smartcampus.mobileapi.param;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author SGS
 */
@Data
public class CrQueryParam {

    /**
     * 用户编号
     */
    @NotNull(message = "userNumber 不能为空")
    private String userNumber;

    /**
     * 卡号
     */
    @NotNull(message = "cardNumber 不能为空")
    private String cardNumber;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
