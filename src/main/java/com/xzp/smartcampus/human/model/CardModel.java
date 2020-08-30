package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.human.vo.FeatureCardVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import com.xzp.smartcampus.human.vo.IModelToFeatureVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_feature_card")
public class CardModel extends BaseModel implements IModelToFeatureVo {

    /**
     * 申请中(opening)
     */
    public static final String STATUS_OPENING = "opening";

    /**
     * 补办中(make-up)
     */
    public static final String STATUS_MAKE_UP = "make-up";

    /**
     * 正常使用(complete)
     */
    public static final String STATUS_COMPLETE = "complete";

    /**
     * 取消(cancel)
     */
    public static final String STATUS_CANCEL = "cancel";

    /**
     * ,不可用(discard)
     */
    public static final String STATUS_DISCARD = "discard";

    /**
     * 申请开通（opening）
     */
    public static final String SERVICE_TYPE_OPENING = "opening";

    /**
     * 挂失补办（make-up）
     */
    public static final String SERVICE_TYPE_MAKE_UP = "make-up";

    /**
     * 卡的类型 IC
     */
    public static final String CARD_TYPE_IC = "ICCard";

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
     * 卡的状态 申请中(opening)，补办中(make-up),正常使用(complete),取消(cancel),不可用(discard)
     */
    private String cardStatus;

    /**
     * 业务类型，申请开通（opening），挂失补办（make-up）
     */
    private String serviceType;

    /**
     * 卡业务描述
     */
    private String reason;

    /**
     * 额度
     */
    private BigDecimal quota;

    /**
     * 描述
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;

    @Override
    public IFeatureVo toFeatureVo() {
        FeatureCardVo cardVo = new FeatureCardVo();
        BeanUtils.copyProperties(this, cardVo);
        return cardVo;
    }
}
