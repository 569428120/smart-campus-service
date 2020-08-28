package com.xzp.smartcampus.mobileapi.vo;

import com.xzp.smartcampus.human.model.CardModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * 移动端卡的vo对象
 *
 * @author Administrator
 */
@Data
public class CardVo {

    /**
     * id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户编码
     */
    private String userNumber;

    /**
     * 卡的类型
     */
    private String cardType;

    /**
     * 卡的状态
     */
    private String cardStatus;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 描述
     */
    private String description;
}
