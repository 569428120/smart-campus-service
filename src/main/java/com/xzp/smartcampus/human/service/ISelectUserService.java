package com.xzp.smartcampus.human.service;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import com.xzp.smartcampus.human.vo.IModelToFeatureVo;
import com.xzp.smartcampus.human.vo.UserGroupTreeVo;
import com.xzp.smartcampus.human.vo.UserVo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ISelectUserService {

    /**
     * 根据用户类型获取用户分组
     *
     * @param searchValue 搜索条件
     * @param userType    用户类型
     * @return List<UserGroupTreeVo>
     */
    List<UserGroupTreeVo> getUserGroupTreeList(UserGroupTreeVo searchValue, String userType);

    /**
     * 分页查询用户
     *
     * @param searchValue searchValue
     * @param groupId     groupId
     * @param userType    userType
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<UserVo>
     */
    PageResult<UserVo> getUserList(UserVo searchValue, String groupId, String userType, Integer current, Integer pageSize);

    /**
     * 获得用户id
     *
     * @param searchValue searchValue
     * @return List<String>
     */
    List<String> getUserIds(IFeatureVo searchValue);

    /**
     * 转换为特征值vo
     *
     * @param featureVos featureVos
     * @return List<IFeatureVo>
     */
    List<IFeatureVo> toFeatureCardVoList(List<? extends IModelToFeatureVo> featureVos);

    /**
     * 获取用户vo
     *
     * @param userId 用户id 学生或者老师
     * @return UserVo
     */
    UserVo getUserVoById(String userId);

    /**
     * 获取用户列表
     *
     * @param userIds userIds
     * @return List<UserVo>
     */
    List<UserVo> getUserListVoByIds(Collection<String> userIds);
}
