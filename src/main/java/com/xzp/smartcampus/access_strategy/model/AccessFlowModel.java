package com.xzp.smartcampus.access_strategy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_guard_ser_flow")
public class AccessFlowModel extends BaseModel {
    private String flowId;
    private String startTime;
    private String endTime;
    private Integer isCar;
    private String carNumber;
}
