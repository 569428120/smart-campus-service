package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import com.xzp.smartcampus.mobileapi.param.FacePostParam;
import com.xzp.smartcampus.mobileapi.param.FaceQueryParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
public interface IFaceManageService {

    /**
     * 根据用户id获取脸部图片
     *
     * @param userId userId
     * @return FaceVo
     */
    IFeatureVo getFaceVoByUserId(@NotNull(message = "userId 不能空") String userId);

    /**
     * 获取当前登录用户的脸部信息，学生和老师查询自己的   家长查询学生的
     *
     * @return List<FaceVo>
     */
    List<IFeatureVo> getMyFaceList();

    /**
     * 分页查询脸部数据
     *
     * @param queryParam 查询条件
     * @return PageResult<FaceVo>
     */
    PageResult<IFeatureVo> getFaceVoPage(@Valid FaceQueryParam queryParam, Integer current, Integer pageSize);

    /**
     * 保存数据
     *
     * @param postParam 参数
     */
    void saveFaceData(@Valid FacePostParam postParam);

    /**
     * 删除数据
     *
     * @param faceIds faceIds
     */
    void deleteFaceByIds(List<String> faceIds);
}
