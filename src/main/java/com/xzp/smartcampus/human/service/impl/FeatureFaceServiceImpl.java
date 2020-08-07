package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.DataUtil;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.FaceMapper;
import com.xzp.smartcampus.human.model.FaceModel;
import com.xzp.smartcampus.human.model.ImageModel;
import com.xzp.smartcampus.human.service.IFeatureFaceService;
import com.xzp.smartcampus.human.service.IImageService;
import com.xzp.smartcampus.human.service.ISelectUserService;
import com.xzp.smartcampus.human.vo.FeatureFaceVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuzhipeng
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FeatureFaceServiceImpl extends IsolationBaseService<FaceMapper, FaceModel> implements IFeatureFaceService {

    @Resource
    private ISelectUserService selectUserService;

    @Resource
    private IImageService imageService;

    /**
     * 分页查询
     *
     * @param searchValue searchValue
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<FeatureFaceVo>
     */
    @Override
    public PageResult<IFeatureVo> getFeatureFaceVoList(FeatureFaceVo searchValue, Integer current, Integer pageSize) {
        // 为了确保性能，必须传入用户类型
        if (StringUtils.isBlank(searchValue.getUserType())) {
            log.warn("userType is null");
            return PageResult.emptyPageResult();
        }
        List<String> userIds = selectUserService.getUserIds(searchValue);
        PageResult<FaceModel> pageResult = this.getFaceModelPage(userIds, current, pageSize);
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), this.toFaceVoList(pageResult.getData()));
    }

    /**
     * 转换为vo
     *
     * @param data data
     * @return List<IFeatureVo>
     */
    private List<IFeatureVo> toFaceVoList(List<FaceModel> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        List<String> imageIds = new ArrayList<>(data.size());
        if (!CollectionUtils.isEmpty(data)) {
            data.forEach(item -> {
                imageIds.addAll(DataUtil.strToList(item.getImageIds(), Constant.PARAMS_ID_SEPARATOR));
            });
        }
        Map<String, ImageModel> imageIdToModelMap = this.getImageIdToModelMap(imageIds);
        List<IFeatureVo> featureVos = selectUserService.toFeatureCardVoList(data);
        featureVos.forEach(item -> {
            FeatureFaceVo faceVo = (FeatureFaceVo) item;
            List<String> ids = DataUtil.strToList(faceVo.getImageIds(), Constant.PARAMS_ID_SEPARATOR);
            List<ImageModel> imageModels = new ArrayList<>(ids.size());
            ids.forEach(imageId -> {
                ImageModel imageModel = imageIdToModelMap.get(imageId);
                if (imageModel == null) {
                    log.warn("ImageModel not exist imageId {}", imageId);
                    return;
                }
                imageModels.add(imageModel);
            });
            faceVo.setImageList(imageModels);
        });
        return featureVos;
    }

    /**
     * id映射图片
     *
     * @param imageIds imageIds
     * @return 映射关系
     */
    private Map<String, ImageModel> getImageIdToModelMap(List<String> imageIds) {
        if (CollectionUtils.isEmpty(imageIds)) {
            return Collections.emptyMap();
        }
        List<ImageModel> imageModels = imageService.selectByIds(imageIds);
        if (CollectionUtils.isEmpty(imageModels)) {
            return Collections.emptyMap();
        }
        return imageModels.stream().collect(Collectors.toMap(ImageModel::getId, v -> v));
    }


    /**
     * 分页查询model
     *
     * @param userIds  userIds
     * @param current  current
     * @param pageSize pageSize
     * @return PageResult<FaceModel>
     */
    private PageResult<FaceModel> getFaceModelPage(List<String> userIds, Integer current, Integer pageSize) {
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<FaceModel>()
                .in(!CollectionUtils.isEmpty(userIds), "user_id", userIds)
                .orderByDesc("create_time")
        );
    }

    /**
     * 保存
     *
     * @param faceModel faceModel
     * @return FaceModel
     */
    @Override
    public FaceModel saveFaceModel(FaceModel faceModel) {
        // 先校验
        this.validatorFaceModel(faceModel);
        if (StringUtils.isBlank(faceModel.getId())) {
            return this.createFaceModel(faceModel);
        }
        return this.updateFaceModel(faceModel);
    }

    /**
     * 更新操作
     *
     * @param faceModel faceModel
     * @return FaceModel
     */
    private FaceModel updateFaceModel(FaceModel faceModel) {
        if (StringUtils.isBlank(faceModel.getId())) {
            log.warn("id is null");
            throw new SipException("参数错误，更新操作id不能为空");
        }
        FaceModel localModel = this.selectById(faceModel.getId());
        if (localModel == null) {
            log.error("FaceModel not exist,id {}", faceModel.getId());
            throw new SipException("参数错误，FaceModel不存在id为 " + faceModel.getId());
        }
        // 需要删除的文件
        List<String> delImageIds = this.getDeleteImageIds(DataUtil.strToList(localModel.getImageIds(), Constant.PARAMS_ID_SEPARATOR),
                DataUtil.strToList(faceModel.getImageIds(), Constant.PARAMS_ID_SEPARATOR));
        if (!CollectionUtils.isEmpty(delImageIds)) {
            imageService.deleteImageByIds(delImageIds);
        }
        // 确认文件
        imageService.confirmImage(DataUtil.strToList(faceModel.getImageIds(), Constant.PARAMS_ID_SEPARATOR));
        localModel.setImageIds(faceModel.getImageIds());
        localModel.setDescription(faceModel.getDescription());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 获取需要删除的图片
     *
     * @param lImageIds lImageIds
     * @param nImageIds nImageIds
     * @return List<String>
     */
    private List<String> getDeleteImageIds(List<String> lImageIds, List<String> nImageIds) {
        if (CollectionUtils.isEmpty(nImageIds) && CollectionUtils.isEmpty(lImageIds)) {
            return Collections.emptyList();
        }
        // 传入的为空，则删除本地
        if (CollectionUtils.isEmpty(nImageIds)) {
            return lImageIds;
        }
        // 本地为空就不需要删除
        if (CollectionUtils.isEmpty(lImageIds)) {
            return Collections.emptyList();
        }
        // 删除本地存在，传入的不存在
        List<String> deleteIds = new ArrayList<>(lImageIds.size());
        lImageIds.forEach(id -> {
            if (!nImageIds.contains(id)) {
                deleteIds.add(id);
            }
        });
        return deleteIds;
    }

    /**
     * 创建
     *
     * @param faceModel faceModel
     * @return FaceModel
     */
    private FaceModel createFaceModel(FaceModel faceModel) {
        if (StringUtils.isBlank(faceModel.getUserId())) {
            log.warn("userId is null");
            throw new SipException("参数错误，userId为空");
        }
        faceModel.setId(SqlUtil.getUUId());
        // 确认文件
        imageService.confirmImage(DataUtil.strToList(faceModel.getImageIds(), Constant.PARAMS_ID_SEPARATOR));
        this.insert(faceModel);
        return faceModel;
    }

    /**
     * 数据校验
     *
     * @param faceModel faceModel
     */
    @Override
    public void validatorFaceModel(FaceModel faceModel) {
        // 用户id不能重复
        if (StringUtils.isNotBlank(faceModel.getUserId())) {
            List<FaceModel> faceModels = this.selectList(new QueryWrapper<FaceModel>()
                    .eq("user_id", faceModel.getUserId())
                    .notIn(StringUtils.isNotBlank(faceModel.getId()), "id", faceModel.getId())
            );
            if (!CollectionUtils.isEmpty(faceModels)) {
                log.warn("faceModels exist user_id {}", faceModel.getUserId());
                throw new SipException("参数错误，该用户人脸信息存在");
            }
        }
    }

    /**
     * 删除记录
     *
     * @param faceIds faceIds
     */
    @Override
    public void deleteFaceByIds(List<String> faceIds) {
        if (CollectionUtils.isEmpty(faceIds)) {
            log.warn("faceIds is null");
            return;
        }
        List<FaceModel> faceModels = this.selectByIds(faceIds);
        if (CollectionUtils.isEmpty(faceModels)) {
            log.warn("not find faceModels by ids {}", faceIds);
            return;
        }
        List<String> imageIds = new ArrayList<>(faceIds.size());
        faceModels.forEach(item -> {
            imageIds.addAll(DataUtil.strToList(item.getImageIds(), Constant.PARAMS_ID_SEPARATOR));
        });
        imageService.deleteImageByIds(imageIds);
        this.deleteByIds(faceIds);
    }
}
