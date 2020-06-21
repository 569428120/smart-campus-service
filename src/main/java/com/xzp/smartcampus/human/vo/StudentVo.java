package com.xzp.smartcampus.human.vo;

import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.model.StudentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentVo extends StudentModel {

    /**
     * 分组名称 xx/xx
     */
    private String groupName;

    /**
     * 联系人列表
     */
    private List<StudentContactModel> studentContactList;
}
