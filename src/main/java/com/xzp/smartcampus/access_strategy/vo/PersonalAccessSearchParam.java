package com.xzp.smartcampus.access_strategy.vo;

import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalAccessSearchParam extends BaseModel {
    private String userName;
    private String userCode;
    private String userType;
    private String strategyType;
    private String mode;
    private String inOrOut;
}
