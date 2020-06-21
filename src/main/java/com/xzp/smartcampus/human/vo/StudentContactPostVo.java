package com.xzp.smartcampus.human.vo;

import com.xzp.smartcampus.human.model.StudentContactModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class StudentContactPostVo {
    /**
     * 联系人列表
     */
    private List<StudentContactModel> contactList;

    /**
     * 学生id
     */
    private String studentId;
}
