package com.xzp.smartcampus.human.vo;

import com.xzp.smartcampus.human.model.StudentGroupModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;


/**
 * @author SGS
 */
@Data
public class ClassVo {

    private String id;

    private String name;

    private String number;

    /**
     * model转换为vo
     *
     * @param groupModel groupModel
     * @return ClassVo
     */
    public static ClassVo newInstance(StudentGroupModel groupModel) {
        ClassVo classVo = new ClassVo();
        BeanUtils.copyProperties(groupModel, classVo);
        classVo.setName(groupModel.getGroupName());
        classVo.setNumber(groupModel.getGroupCode());
        return classVo;
    }
}
