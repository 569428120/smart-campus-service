package com.xzp.smartcampus.mobileapi.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author SGS
 */
@Data
public class ExamineCardPostParam {

    @NotNull(message = "cardId 不能为空")
    private String cardId;
    @NotNull(message = "cardType 不能为空")
    private String cardType;
    @NotNull(message = "cardNumber 不能为空")
    private String cardNumber;
}
