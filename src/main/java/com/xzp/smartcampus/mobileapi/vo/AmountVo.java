package com.xzp.smartcampus.mobileapi.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@lombok.Data
public class AmountVo {

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 操作时间
     */
    private String operationTime;

    /**
     * 场景
     */
    private String scene;
}
