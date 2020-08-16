package com.xzp.smartcampus.mobileapi.param;


import lombok.Data;

import java.util.Date;

@Data
public class CrQueryParam {

    /**
     * 用户编号
     */
    private String userNumber;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
