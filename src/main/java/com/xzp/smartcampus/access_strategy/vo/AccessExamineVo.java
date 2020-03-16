package com.xzp.smartcampus.access_strategy.vo;

import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccessExamineVo extends BaseModel {
    private String originatorId;
    private String applicantId;
    private String examineId;
    private String startTime;
    private String endTime;
    private String reason;
    private Integer isCar;
    private String carNumber;
    private String flowType;
}
