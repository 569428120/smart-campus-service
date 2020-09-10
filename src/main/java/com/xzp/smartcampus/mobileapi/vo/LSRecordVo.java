package com.xzp.smartcampus.mobileapi.vo;

import com.xzp.smartcampus.mobileapi.model.LSRecordModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LSRecordVo extends LSRecordModel {
    // 班级名称
    private String className;
}
