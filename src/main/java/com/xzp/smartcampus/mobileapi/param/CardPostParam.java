package com.xzp.smartcampus.mobileapi.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author SGS
 */
@Data
public class CardPostParam {

    private String id;

    @NotNull(message = "userId 不能为空")
    private String userId;

    @NotNull(message = "serviceType 不能为空")
    private String serviceType;

    private String originalCardNumber;

    @NotNull(message = "cardNumber 不能为空")
    private String cardNumber;

    @NotNull(message = "cardType 不能为空")
    private String cardType;
}
