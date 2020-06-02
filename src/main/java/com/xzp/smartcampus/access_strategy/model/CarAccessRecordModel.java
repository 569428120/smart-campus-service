package com.xzp.smartcampus.access_strategy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_access_ser_car_record")
public class CarAccessRecordModel extends BaseModel {
    private String userId;
    private String userName;
    private String userCode;
    private String userType;
    private String strategyType;
    private String strategyId;
    private String mode;
    private String deviceId;
    private String inOrOut;
    private String carNumber;

}
