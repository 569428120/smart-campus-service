package com.xzp.smartcampus.access_strategy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_access_ser_strategy")
public class AccessStrategyModel extends BaseModel {
    // 策略名称
    private String strategyName;
    // 状态
    private String strategyStatus;
    // 策略编码
    private String strategyCode;
    // 描述信息
    private String description;
}
