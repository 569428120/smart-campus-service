package com.xzp.smartcampus.authority.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SGS
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_authority_role")
public class RoleModel extends BaseModel {

    /**
     * 默认教师和职员权限
     */
    public static final String ROLE_TYPE_DEFAULT_TEACHER = "default_teacher";

    /**
     * 默认家长和学生的权限
     */
    public static final String ROLE_TYPE_DEFAULT_STUDENT = "default_student";

    private String roleName;
    private String roleDesc;
    private String roleType;
}
