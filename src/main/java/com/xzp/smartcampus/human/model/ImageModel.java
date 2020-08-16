package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xuzhipeng
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_image")
public class ImageModel extends BaseModel {

    /**
     * 人员特征人脸业务
     */
    public static final String FACE_IMAGE = "faceImage";

    /**
     * key
     */
    private String fileKey;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String url;

    /**
     * 业务类型
     */
    private String serviceType;

    /**
     * 状态
     */
    private String status;
}

