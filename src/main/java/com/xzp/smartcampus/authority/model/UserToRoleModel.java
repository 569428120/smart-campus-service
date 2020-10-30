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
@TableName(value = "tb_authority_user_to_role")
public class UserToRoleModel extends BaseModel {

    /**
     * tb_human_ser_staff的id
     */
    private String userId;

    /**
     * 角色id
     */
    private String atyRoleId;

}
