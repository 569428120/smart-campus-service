package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_feature_card")
public class CardModel extends BaseModel {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 卡类型
     */
        private String cardType;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 描述
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;
}
