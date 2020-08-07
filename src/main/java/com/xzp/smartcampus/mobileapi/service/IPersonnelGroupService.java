package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.mobileapi.vo.PersonnelGroupVo;

import java.util.List;

/**
 * @author SGS
 */
public interface IPersonnelGroupService {

    /**
     * 获取用户分组
     *
     * @param nodeId   节点id
     * @param withUser 是否查询用户
     * @return List<PersonnelGroupVo>
     */
    List<PersonnelGroupVo> getPersonnelGroupByNodeId(String nodeId, Boolean withUser);
}
