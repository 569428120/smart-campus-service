package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.enums.FileStatusType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.OssUtil;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.config.AliyunOssConfig;
import com.xzp.smartcampus.human.mapper.ImageMapper;
import com.xzp.smartcampus.human.model.ImageModel;
import com.xzp.smartcampus.human.service.IImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author xuzhipeng
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AliOssImageServiceImpl extends IsolationBaseService<ImageMapper, ImageModel> implements IImageService {

    @Resource
    private OssUtil ossUtil;

    @Resource
    private AliyunOssConfig ossConfig;

    /**
     * 确认文件，并且删除过期的文件
     *
     * @param imageIds imageIds
     */
    @Override
    public void confirmImage(List<String> imageIds) {
        if (CollectionUtils.isEmpty(imageIds)) {
            log.warn("imageIds is null");
            return;
        }
        List<ImageModel> imageModels = this.selectByIds(imageIds);
        if (CollectionUtils.isEmpty(imageModels)) {
            log.warn("not find imageModels by imageIds {}", imageIds);
            return;
        }
        imageModels.forEach(item -> {
            item.setStatus(FileStatusType.DONE.getKey());
        });
        this.updateBatch(imageModels);
        // 清理过期的文件
        this.clearOverdueImageFile();
    }

    /**
     * 清理过期未确认的文件
     */
    private void clearOverdueImageFile() {
        Date overdueDate = DateUtils.addDays(new Date(), -1);
        List<ImageModel> imageModels = this.selectList(new QueryWrapper<ImageModel>()
                .eq("status", FileStatusType.TO_BE_CONFIRMED.getKey())
                .le("create_time", overdueDate)
        );
        if (!CollectionUtils.isEmpty(imageModels)) {
            this.deleteImageByModels(imageModels);
        }
    }

    /**
     * 删除图片，并把oss中的文件删除
     *
     * @param imageIds imageIds
     */
    @Override
    public void deleteImageByIds(List<String> imageIds) {
        if (CollectionUtils.isEmpty(imageIds)) {
            return;
        }
        List<ImageModel> imageModels = this.selectByIds(imageIds);
        this.deleteImageByModels(imageModels);
    }

    /**
     * 根据对象删除
     *
     * @param imageModels imageModels
     */
    private void deleteImageByModels(List<ImageModel> imageModels) {
        if (CollectionUtils.isEmpty(imageModels)) {
            return;
        }
        List<String> ids = new ArrayList<>(imageModels.size());
        List<String> keys = new ArrayList<>(imageModels.size());
        imageModels.forEach(item -> {
            ids.add(item.getId());
            keys.add(item.getFileKey());
        });
        ossUtil.deleteByKeys(keys);
        this.deleteByIds(ids);
    }

    /**
     * 上传图片
     *
     * @param imageFile imageFile
     * @return ImageModel
     */
    @Override
    public ImageModel uploadImage(MultipartFile imageFile) {
        ImageModel imageModel = null;
        try {
            String filename = imageFile.getOriginalFilename();
            String fileId = SqlUtil.getUUId();
            String fileUrl = ossUtil.uploadFile(imageFile.getInputStream(), fileId, filename);
            imageModel = new ImageModel();
            imageModel.setId(fileId);
            imageModel.setFileKey(fileUrl);
            imageModel.setName(filename);
            imageModel.setStatus(FileStatusType.TO_BE_CONFIRMED.getKey());
            imageModel.setUrl(ossConfig.getBucketUrl() + fileUrl);
            imageModel.setServiceType(ImageModel.FACE_IMAGE);
            this.insert(imageModel);
        } catch (Exception e) {
            log.error("upload error", e);
            throw new SipException("上传错误: " + e.getMessage());
        }
        return imageModel;
    }

    /**
     * 批量上传
     *
     * @param imageFiles imageFiles
     * @return List<ImageModel>
     */
    @Override
    public List<ImageModel> uploadImageList(List<MultipartFile> imageFiles) {
        return null;
    }
}
