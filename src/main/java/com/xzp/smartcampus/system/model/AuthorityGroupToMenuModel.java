package com.xzp.smartcampus.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_sys_ser_authority_to_menu")
public class AuthorityGroupToMenuModel extends BaseModel {
    /**
     * 权限组id
     */
    private String groupId;
    /**
     * 菜单id
     */
    private String menuId;
}
