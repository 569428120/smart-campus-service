package com.xzp.smartcampus.mobileapi.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName(value = "tb_leaveschool_ser_examine_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class LSExamineUserModel extends BaseModel {
    // 审核人id
    private String examineUserId;
}
