package com.xzp.smartcampus.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_sys_ser_menu")
public class AuthorityGroupModel extends BaseModel {

    /**
     * 是否为模板
     */
    private Boolean template;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 权限编码
     */
    private String authorityCode;

    /**
     * 描述
     */
    private String description;
}
