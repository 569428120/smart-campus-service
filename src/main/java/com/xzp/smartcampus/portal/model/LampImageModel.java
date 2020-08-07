package com.xzp.smartcampus.portal.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SGS
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_sys_ser_lamp_image")
public class LampImageModel extends BaseModel {

    /**
     * 跑马灯类型  主页，资源页，app页
     */
    private String lampType;

    /**
     * 图片的名称
     */
    private String name;

    /**
     * 图片的url
     */
    private String url;

    /**
     * 描述
     */
    private String description;
}
