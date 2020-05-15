package com.xzp.smartcampus.device.agent.hpt.api;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.device.agent.hpt.dto.FaceCardDto;
import com.xzp.smartcampus.device.agent.hpt.dto.FaceCardSysParamVo;
import com.xzp.smartcampus.device.agent.hpt.utils.RestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 人脸识别一体机
 */
@Component
@Slf4j
public class FaceCardMachineApi {

    @Resource
    private RestTemplate template;


    /**
     * 根据网络地址获取设备参数
     *
     * @param netAddress 网络地址 ip加端口
     * @param userName   设备账号
     * @param password   设备密码
     * @return FaceCardSysParamVo
     */
    public FaceCardSysParamVo getSysParam(String netAddress, String userName, String password) {
        FaceCardDto dto = RestUtils.httpPostDto(template, netAddress, userName, password, null);
        Map<String, Object> info = dto.getInfo();
        FaceCardSysParamVo paramVo = new FaceCardSysParamVo();
        paramVo.setName(info.getOrDefault("Name", "").toString());
        paramVo.setDeviceId(info.getOrDefault("DeviceId", "").toString());
        paramVo.setVersion(info.getOrDefault("Version", "").toString());
        return paramVo;
    }

    /**
     * 开门控制
     *
     * @param netAddress 网络地址 ip加端口
     * @param userName   设备账号
     * @param password   设备密码
     * @param msg        消息
     */
    public void openGate(String netAddress, String userName, String password, String deviceId, String msg) {
        FaceCardDto postDto = new FaceCardDto();
        postDto.setOperator("OpenDoor");
        postDto.getInfo().put("DeviceID", deviceId);
        postDto.getInfo().put("status", 1);
        postDto.getInfo().put("msg", msg);
        FaceCardDto dto = RestUtils.httpPostDto(template, netAddress, userName, password, postDto);
        // 是否请求成功
        if (dto.getCode() != 200) {
            throw new SipException("开门失败，状态码为 " + dto.getCode());
        }
    }

    /**
     * 设置消息订阅
     *
     * @param netAddress 网络地址 ip加端口
     * @param userName   设备账号
     * @param password   设备密码
     * @param deviceId   设备id
     * @param subHost    订阅服务器地址
     */
    public void setSubscribe(String netAddress, String userName, String password, String deviceId, String subHost) {
        Map<String, String> topics = new LinkedHashMap<>();
        topics.put("Card", "/agent/hpt/face-card/VerifyCard");
        topics.put("VerifyWithSnap", "/agent/hpt/face-card/VerifyWithSnap");
        // 心跳
        topics.put("HeartBeat", "/agent/hpt/face-card/heart-beat");
        FaceCardDto postDto = new FaceCardDto();
        postDto.setOperator("Subscribe");
        postDto.getInfo().put("DeviceID", deviceId);
        postDto.getInfo().put("Num", topics.size() - 1);
        postDto.getInfo().put("Topics", topics.keySet().stream().filter(key -> !"HeartBeat".equals(key)).collect(Collectors.toList()));
        postDto.getInfo().put("SubscribeAddr", subHost);
        postDto.getInfo().put("SubscribeUrl", topics);
        postDto.getInfo().put("BeatInterval", 30);
        postDto.getInfo().put("ResumefromBreakpoint", 0);
        postDto.getInfo().put("Auth", "none");
        FaceCardDto dto = RestUtils.httpPostDto(template, MessageFormat.format("http://{0}/action/Subscribe", netAddress), userName, password, postDto);
        // 是否请求成功
        if (dto.getCode() != 200) {
            throw new SipException("设置订阅失败，状态码为 " + dto.getCode());
        }
    }
}
