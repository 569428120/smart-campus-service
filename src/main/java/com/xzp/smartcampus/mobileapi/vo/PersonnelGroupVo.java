package com.xzp.smartcampus.mobileapi.vo;

import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.human.model.*;
import lombok.Data;

import java.util.List;

/**
 * @author SGS
 */
@Data
public class PersonnelGroupVo {

    /**
     * 分组
     */
    public static final String GROUP_TYPE_GROUP = "group";

    /**
     * 人员
     */
    public static final String GROUP_TYPE_ENTITY = "entity";

    /**
     *
     */
    public static final String GROUP_ROOT_STAFF = "学校职工组";

    /**
     *
     */
    public static final String GROUP_ROOT_STUDENT = "班级分组";

    /**
     * id
     */
    private String id;

    /**
     * 分组(group) 人员(entity)
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 人员编号
     */
    private String number;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 手机号码
     */
    private String contact;

    /**
     * 所在分组的id
     */
    private String groupId;

    /**
     * 所在分组的名称
     */
    private String groupName;

    /**
     * 联系人z
     */
    private List<PersonnelGroupVo> parentList;

    /**
     * 转为Vo对象
     *
     * @param item staffGroupModel
     * @return PersonnelGroupVo
     */
    public static PersonnelGroupVo newInstance(StaffGroupModel item) {
        PersonnelGroupVo groupVo = new PersonnelGroupVo();
        groupVo.setId(item.getId());
        groupVo.setType(PersonnelGroupVo.GROUP_TYPE_GROUP);
        groupVo.setName(item.getGroupName());
        groupVo.setNumber(item.getGroupCode());
        groupVo.setGroupId(item.getPid());
        return groupVo;
    }

    /**
     * 转为vo对象
     *
     * @param item StudentGroupModel
     * @return PersonnelGroupVo
     */
    public static PersonnelGroupVo newInstance(StudentGroupModel item) {
        PersonnelGroupVo groupVo = new PersonnelGroupVo();
        groupVo.setId(item.getId());
        groupVo.setName(item.getGroupName());
        groupVo.setType(PersonnelGroupVo.GROUP_TYPE_GROUP);
        groupVo.setNumber(item.getGroupCode());
        groupVo.setGroupId(item.getPid());
        return groupVo;
    }


    /**
     * 转换为vo
     *
     * @param staffModel staffModel
     * @return PersonnelGroupVo
     */
    public static PersonnelGroupVo newInstance(StaffModel staffModel) {
        PersonnelGroupVo groupVo = new PersonnelGroupVo();
        groupVo.setId(staffModel.getId());
        groupVo.setType(PersonnelGroupVo.GROUP_TYPE_ENTITY);
        groupVo.setGroupId(staffModel.getGroupId());
        groupVo.setName(staffModel.getName());
        groupVo.setNumber(staffModel.getUserJobCode());
        groupVo.setUserType(staffModel.getUserType());
        groupVo.setContact(staffModel.getContact());
        return groupVo;
    }

    /**
     * 转换为vo
     *
     * @param studentModel studentModel
     * @param parentList   parentList
     * @return PersonnelGroupVo
     */
    public static PersonnelGroupVo newInstance(StudentModel studentModel, List<PersonnelGroupVo> parentList) {
        PersonnelGroupVo groupVo = new PersonnelGroupVo();
        groupVo.setId(studentModel.getId());
        groupVo.setType(PersonnelGroupVo.GROUP_TYPE_ENTITY);
        groupVo.setGroupId(studentModel.getGroupId());
        groupVo.setName(studentModel.getName());
        groupVo.setNumber(studentModel.getStudentCode());
        groupVo.setUserType(UserType.STUDENT.getKey());
        groupVo.setParentList(parentList);
        return groupVo;
    }

    /**
     * 转换为vo
     *
     * @param item item
     * @return PersonnelGroupVo
     */
    public static PersonnelGroupVo newInstance(StudentContactModel item) {
        PersonnelGroupVo groupVo = new PersonnelGroupVo();
        groupVo.setId(item.getId());
        groupVo.setType(PersonnelGroupVo.GROUP_TYPE_ENTITY);
        groupVo.setUserType(UserType.PARENT.getKey());
        groupVo.setName(item.getName());
        groupVo.setContact(item.getContact());
        return groupVo;
    }
}
