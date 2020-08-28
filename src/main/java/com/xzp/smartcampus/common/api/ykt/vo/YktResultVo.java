package com.xzp.smartcampus.common.api.ykt.vo;

import lombok.Data;


/**
 * @author SGS
 */
@Data
public class YktResultVo {

    private String code;

    private String msg;

    private Integer totalCount;

    private Integer totalPage;

    private String data;
}
