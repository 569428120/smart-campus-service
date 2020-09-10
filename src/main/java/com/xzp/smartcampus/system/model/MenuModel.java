package com.xzp.smartcampus.system.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_sys_ser_menu")
public class MenuModel extends BaseModel {

    /**
     * 父节点id
     */
    private String pid;

    /**
     * 树结构
     */
    private String treePath;

    /**
     * 类型 pc 还是app
     */
    private String appType;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单级别
     */
    private Integer menuLevel;

    /**
     * 路由
     */
    private String route;

    /**
     * 操作名称
     */
    private String operateName;

    /**
     * 操作码
     */
    private String operateCode;

    /**
     * 图标地址
     */
    private String iconUrl;

    /**
     * 描述
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;
}
