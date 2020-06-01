package com.xzp.smartcampus.human.vo;

import com.xzp.smartcampus.human.model.StaffModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends StaffModel {

    /**
     * 分组名称 xx/xx
     */
    private String groupName;
}
