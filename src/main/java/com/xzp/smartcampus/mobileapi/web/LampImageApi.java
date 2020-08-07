package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.enums.LampType;
import com.xzp.smartcampus.portal.model.LampImageModel;
import com.xzp.smartcampus.portal.service.ILampImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhipeng
 */
@RestController
@RequestMapping("/mobileApi")
public class LampImageApi {

    @Resource
    private ILampImageService lampImageService;

    /**
     * 主页
     *
     * @return List<LampImageModel>
     */
    @GetMapping("/home/lamp-image/gets")
    public ResponseEntity<List<LampImageModel>> getHomeLampImageList() {
        return ResponseEntity.ok(lampImageService.getLampImageListByLampType(LampType.HOME));
    }

    /**
     * 资源页
     *
     * @return List<LampImageModel>
     */
    @GetMapping("/resource-home/lamp-image/gets")
    public ResponseEntity<List<LampImageModel>> getResourceHomeLampImageList() {
        return ResponseEntity.ok(lampImageService.getLampImageListByLampType(LampType.RESOURCE_HOME));
    }

    /**
     * app页
     *
     * @return List<LampImageModel>
     */
    @GetMapping("/app-home/lamp-image/gets")
    public ResponseEntity<List<LampImageModel>> getAppHomeLampImageList() {
        return ResponseEntity.ok(lampImageService.getLampImageListByLampType(LampType.APP_HOME));
    }
}
