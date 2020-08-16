package com.xzp.smartcampus.human.vo;

import com.xzp.smartcampus.human.model.CardModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeatureCardVo extends CardModel implements IFeatureVo {

    /**
     * 分组
     */
    private String groupName;

    /**
     * 身份证或者学号
     */
    private String userCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户对象
     */
    private UserVo userVo;
}
