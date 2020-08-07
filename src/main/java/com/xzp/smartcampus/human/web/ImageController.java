package com.xzp.smartcampus.human.web;


import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.DataUtil;
import com.xzp.smartcampus.human.model.ImageModel;
import com.xzp.smartcampus.human.service.IImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author xuzhipeng
 */
@RestController
@RequestMapping("/human/image")
public class ImageController {

    @Resource
    private IImageService imageService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<ImageModel> upload(MultipartFile file) {
        return ResponseEntity.ok(imageService.uploadImage(file));
    }

    @PostMapping("/multi-upload")
    @ResponseBody
    public ResponseEntity<List<ImageModel>> multiUpload(List<MultipartFile> files) {
        return ResponseEntity.ok(imageService.uploadImageList(files));
    }

    /**
     * 删除数据
     *
     * @param imageIds imageIds
     * @return ResponseEntity<String>
     */
    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteFaceByIds(@RequestParam(value = "imageIds", defaultValue = "") String imageIds) {
        imageService.deleteImageByIds(DataUtil.strToList(imageIds, Constant.PARAMS_ID_SEPARATOR));
        return ResponseEntity.ok("删除成功");
    }

}
