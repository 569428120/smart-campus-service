package com.xzp.smartcampus.system.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_sys_ser_school")
public class SchoolModel extends BaseModel {


    /**
     * TODO 暂时不用
     */
    private String treePath;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学校级别 完全小学，完全中学，小学&中学
     */
    private String schoolLevel;

    /**
     * 学校类型  公立，私立
     */
    private String schoolType;

    /**
     * 地址
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String address;

    /**
     * 联系方式
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String contact;

    /**
     * 描述
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;

    /**
     * 教育局名称
     */
    @TableField(exist = false)
    private String regionName;
}
