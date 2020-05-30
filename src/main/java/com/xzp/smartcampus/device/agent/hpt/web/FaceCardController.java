package com.xzp.smartcampus.device.agent.hpt.web;

import com.xzp.smartcampus.common.utils.JsonUtils;
import com.xzp.smartcampus.device.agent.hpt.dto.FaceCardDto;
import com.xzp.smartcampus.device.agent.hpt.dto.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供给设备回调使用的接口
 */
@RestController
@RequestMapping("/agent/hpt/face-card")
@Slf4j
public class FaceCardController {

    /**
     * 刷卡认证上报
     *
     * @param postDto postDto
     * @return ResultVo
     */
    @PostMapping("/VerifyCard")
    public ResponseEntity<ResultVo> verifyCard(@RequestBody FaceCardDto postDto) {
        log.info(JsonUtils.toString(postDto));
        return ResponseEntity.ok(ResultVo.ok());
    }

    /**
     * 人脸认证上报
     *
     * @param postDto postDto
     * @return ResultVo
     */
    @PostMapping("/VerifyWithSnap")
    public ResponseEntity<ResultVo> verifyWithSnap(@RequestBody FaceCardDto postDto) {
        log.info(JsonUtils.toString(postDto));
        return ResponseEntity.ok(ResultVo.ok());
    }

    /**
     * 心跳
     *
     * @param postDto postDto
     * @return ResultVo
     */
    @PostMapping("/heart-beat")
    public ResponseEntity<ResultVo> heartBeat(@RequestBody FaceCardDto postDto) {
        log.info(JsonUtils.toString(postDto));
        return ResponseEntity.ok(ResultVo.ok());
    }

}
