package com.xzp.smartcampus.human.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.FaceModel;
import com.xzp.smartcampus.human.vo.FeatureFaceVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;

import java.util.List;

/**
 * @author xuzhipeng
 */
public interface IFeatureFaceService extends IBaseService<FaceModel> {

    /**
     * 分页查询
     *
     * @param searchValue searchValue
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<FeatureFaceVo>
     */
    PageResult<IFeatureVo> getFeatureFaceVoList(FeatureFaceVo searchValue, Integer current, Integer pageSize);

    /**
     * 保存
     *
     * @param faceModel faceModel
     * @return FaceModel
     */
    FaceModel saveFaceModel(FaceModel faceModel);

    /**
     * 数据校验
     *
     * @param faceModel faceModel
     */
    void validatorFaceModel(FaceModel faceModel);

    /**
     * 删除记录
     *
     * @param faceIds faceIds
     */
    void deleteFaceByIds(List<String> faceIds);
}
