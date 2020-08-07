package com.xzp.smartcampus.human.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xuzhipeng
 */
public interface IImageService extends IBaseService<ImageModel> {

    /**
     * 确认文件，并且删除过期的文件
     *
     * @param imageIds imageIds
     */
    void confirmImage(List<String> imageIds);

    /**
     * 删除图片，并把oss中的文件删除
     *
     * @param imageIds imageIds
     */
    void deleteImageByIds(List<String> imageIds);

    /**
     * 上传图片
     *
     * @param imageFile imageFile
     * @return ImageModel
     */
    ImageModel uploadImage(MultipartFile imageFile);

    /**
     * 批量上传
     *
     * @param imageFiles imageFiles
     * @return List<ImageModel>
     */
    List<ImageModel> uploadImageList(List<MultipartFile> imageFiles);
}
