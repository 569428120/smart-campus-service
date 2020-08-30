package com.xzp.smartcampus.mobileapi.vo;


import com.xzp.smartcampus.human.model.StudentGroupModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author SGS
 */
@Data
public class UserGroupVo {

    /**
     * 分组id
     */
    private String id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组编码
     */
    private String groupCode;

    /**
     * 创建实例
     *
     * @param item item
     * @return UserGroupVo
     */
    public static UserGroupVo newInstance(Object item) {
        UserGroupVo groupVo = new UserGroupVo();
        BeanUtils.copyProperties(item, groupVo);
        return groupVo;
    }
}
