package com.xzp.smartcampus.mobileapi.vo;


import lombok.Data;

import java.util.List;

/**
 * @author SGS
 */
@Data
public class GroupNewsVo {

    /**
     * 分组id
     */
    private String id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 新闻
     */
    private List<NewsVo> news;
}
