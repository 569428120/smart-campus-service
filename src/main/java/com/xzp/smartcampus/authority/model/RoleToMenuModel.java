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
@TableName(value = "tb_authority_role_to_menu")
public class RoleToMenuModel extends BaseModel {
    private String atyRoleId;
    private String menuId;
}
