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
    // 状态>>>是否启用>>>0 禁用;1 启用
    private int status;
    // 描述信息
    private String desc;
}
