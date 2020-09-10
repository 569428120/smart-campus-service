package com.xzp.smartcampus.portal.service.impl;


import com.xzp.smartcampus.common.enums.LampType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.NonIsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.portal.mapper.LampImageMapper;
import com.xzp.smartcampus.portal.model.LampImageModel;
import com.xzp.smartcampus.portal.service.ILampImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LampImageServiceImpl extends NonIsolationBaseService<LampImageMapper, LampImageModel> implements ILampImageService {

    /**
     * 跑马灯列表
     *
     * @param lampType 类型
     * @return List<LampImageModel>
     */
    @Override
    public List<LampImageModel> getLampImageListByLampType(LampType lampType) {
        if (lampType == null) {
            log.warn("lampType is null");
            throw new SipException("lampType is null");
        }
        // TODO 先放假数据
        return Stream.of("https://scs-image.oss-cn-beijing.aliyuncs.com/test-image/Koala.jpg", "https://scs-image.oss-cn-beijing.aliyuncs.com/test-image/Lighthouse.jpg").map(url -> {
            LampImageModel lampImageModel = new LampImageModel();
            lampImageModel.setId(SqlUtil.getUUId());
            lampImageModel.setLampType(lampType.getKey());
            lampImageModel.setName("测试");
            lampImageModel.setUrl(url);
            return lampImageModel;
        }).collect(Collectors.toList());
    }
}
