package com.xzp.smartcampus.mobileapi.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 一键放学记录
 */
@Data
@TableName(value = "tb_leaveschool_ser_record")
@EqualsAndHashCode
public class LSRecordModel extends BaseModel {
    private String classId;
    private String createById;
    private String examineById;
    private Date leaveTime;
    private String lsStatus;
    private String description;
}









