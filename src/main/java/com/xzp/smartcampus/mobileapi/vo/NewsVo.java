package com.xzp.smartcampus.mobileapi.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author SGS
 */
@Data
public class NewsVo {

    /**
     * id
     */
    private String id;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 标题图片
     */
    private String titleImageUrl;

    /**
     * 发布时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:MM:SS")
    private Date publishedTime;

    /**
     * 访问量
     */
    private Long visits;

    /**
     * 详情数据
     */
    private String details;
}
