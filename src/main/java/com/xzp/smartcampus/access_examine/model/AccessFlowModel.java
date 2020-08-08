package com.xzp.smartcampus.access_examine.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_access_ser_flow")
public class AccessFlowModel extends BaseModel {
    public static final String FLOW_TYPE_ACCESS = "AccessFlow";

    private String originatorId;   //发起人Id
    private String originatorName;
    private String originatorCode;
    private String applicantType;  //申请人
    private String applicantId;
    private String applicantName;
    private String applicantCode;
    private String examineStatus;  //审核状态
    private String flowName;
    private String flowType;
    private String originatorType;
    private String applicantDesc;  //申请原因
    private String examineDesc;    //审核意见
    private String examineId;      //审批人Id
    private String examineType;
    private String examineName;
    private String examineCode;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:MM:SS")
    private Date examineTime;
    private String startTime;
    private String endTime;
    private Integer isCar;
    private String carNumber;

}
