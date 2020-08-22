package com.xzp.smartcampus.access_strategy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SGS
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_access_ser_strategy_to_group")
public class AccessStrategyToGroupModel extends BaseModel {

    /**
     * 策略id
     */
    private String strategyId;

    /**
     * 分组id
     */
    private String groupId;
}
