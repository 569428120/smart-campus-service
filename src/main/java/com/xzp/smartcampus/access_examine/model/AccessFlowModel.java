package com.xzp.smartcampus.access_examine.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_access_ser_flow")
public class AccessFlowModel extends BaseModel {
    private String originatorId;   //发起人Id
    private String originatorName;
    private String originatorCode;
    private String applicantType;  //申请人
    private String applicantId;
    private String applicantName;
    private String applicantCode;
    private String examineStatus;  //审核状态
    private String flowName;
    private String originatorType;
    private String applicantDesc;  //申请原因
    private String examineDesc;    //审核意见
    private String examineId;      //审批人Id
    private String examineType;
    private String examineName;
    private String examineCode;
    private String startTime;
    private String endTime;
    private Integer isCar;
    private String carNumber;

}
