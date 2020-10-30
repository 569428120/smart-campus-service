package com.xzp.smartcampus.authority.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SGS
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_authority_user")
public class AtyUserModel extends BaseModel {

    /**
     * 手机号
     */
    private String cellNumber;

    /**
     * 公众号openId
     */
    private String wxOpenId;

    /**
     * 设备id
     */
    private String deviceCode;
}
