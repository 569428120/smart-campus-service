package com.xzp.smartcampus.access_strategy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_flow_ser_flow_step")
public class AccessFlowStepModel extends BaseModel {
    private String stepName;
    private String opinion;
    private String handleId;
    private String handleType;
    private String handleName;
    private String handleCode;
    private String handleStatus;

}
