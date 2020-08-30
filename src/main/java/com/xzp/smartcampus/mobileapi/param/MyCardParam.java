package com.xzp.smartcampus.mobileapi.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Data
public class MyCardParam {

    /**
     * 用户id
     */
    @NotNull(message = "userId 不能为空")
    private String userId;

    /**
     * 办理的业务类型 申请开通（opening），挂失补办（make-up）
     */
    @NotNull(message = "serviceType 不能为空")
    private String serviceType;

    /**
     * 审核人id
     */
    @NotNull(message = "examineUserId 不能为空")
    private String examineUserId;

    /**
     * 旧的卡号
     */
    private String oldCardNumber;

    /**
     * 原因
     */
    private String reason;
}
