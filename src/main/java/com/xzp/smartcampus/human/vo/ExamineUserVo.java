package com.xzp.smartcampus.human.vo;

import com.xzp.smartcampus.human.model.ExamineUserModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SGS
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExamineUserVo extends ExamineUserModel {

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 名称
     */
    private String name;

    /**
     * 用户编号
     */
    private String number;

    /**
     * 联系人
     */
    private String contact;
}
