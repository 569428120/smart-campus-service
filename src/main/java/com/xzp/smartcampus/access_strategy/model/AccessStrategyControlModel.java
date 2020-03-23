package com.xzp.smartcampus.access_strategy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_guard_ser_control")
public class AccessStrategyControlModel extends BaseModel {
    private String userGroupId;
    private String strategyId;
    private String controlDesc;
}
