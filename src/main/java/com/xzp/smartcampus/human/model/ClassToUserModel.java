package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SGS
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_class_to_user")
public class ClassToUserModel extends BaseModel {

    /**
     * 班级id
     */
    private String classId;

    /**
     * 用户id
     */
    private String userId;
}
