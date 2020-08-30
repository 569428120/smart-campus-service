package com.xzp.smartcampus.mobileapi.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author SGS
 */
@Data
public class QuotaParam {

    @NotNull(message = "userId 不能为空")
    private String userId;

    @NotNull(message = "cardNumber 不能为空")
    private String cardNumber;

    @NotNull(message = "quota 不能为空")
    private BigDecimal quota;
}
