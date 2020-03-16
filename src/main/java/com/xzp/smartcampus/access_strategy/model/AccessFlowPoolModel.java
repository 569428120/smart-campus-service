package com.xzp.smartcampus.access_strategy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_flow_ser_flow_pool")
public class AccessFlowPoolModel extends BaseModel {
    private String originatorId;
    private String originatorName;
    private String originatorCode;
    private String applicantId;
    private String applicantType;
    private String applicantName;
    private String applicantCode;
    private String steps;
    private String currStep;
    private String examineStatus;
    private String flowType;
    private String serviceId;
}
