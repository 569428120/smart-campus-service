package com.xzp.smartcampus.system.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_sys_ser_region")
public class RegionModel extends BaseModel {

    /**
     * 父节点id
     */
    private String pid;

    /**
     * tree_path
     */
    private String treePath;

    /**
     * 区域名称
     */
    private String regionName;

    /**
     * 教育局名称
     */
    private String educationName;

    /**
     * 描述 IGNORED忽略判断 null也可以使用update方法
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;

}
