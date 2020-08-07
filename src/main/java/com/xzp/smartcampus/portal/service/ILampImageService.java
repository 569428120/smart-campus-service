package com.xzp.smartcampus.portal.service;

import com.xzp.smartcampus.common.enums.LampType;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.portal.model.LampImageModel;

import java.util.List;

/**
 * @author SGS
 */
public interface ILampImageService extends IBaseService<LampImageModel> {

    /**
     * 跑马灯列表
     *
     * @param lampType 类型
     * @return List<LampImageModel>
     */
    List<LampImageModel> getLampImageListByLampType(LampType lampType);
}
