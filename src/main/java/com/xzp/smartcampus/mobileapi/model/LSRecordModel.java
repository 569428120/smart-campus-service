package com.xzp.smartcampus.mobileapi.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;


/**
 * 一键放学记录
 */
@Data
@TableName(value = "tb_leaveschool_ser_record")
@EqualsAndHashCode
public class LSRecordModel extends BaseModel {

    @NotBlank(message = "班级id不能为空")
    private String classId;

    private String createById;

    @NotBlank(message = "审核人不能为空")
    private String examineById;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private Date leaveTime;
    private String lsStatus;
    private String description;
}









