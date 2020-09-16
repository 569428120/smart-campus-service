package com.xzp.smartcampus.human.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SGS
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_examine_user")
public class ExamineUserModel extends BaseModel {

    /**
     * 门禁审核
     */
    public static final String EXAMINE_TYPE_ACCESS = "AccessExamine";

    /**
     * 一卡通审核
     */
    public static final String EXAMINE_TYPE_CARD = "CardExamine";

    /**
     * 一键放学审核人
     */
    public static final String EXAMINE_TYPE_LEAVE_SCHOOL = "LeaveSchoolExamine";

    /**
     * 审核类型
     */
    private String examineType;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 业务id ，比如存班级id
     */
    private String serviceId;
}
