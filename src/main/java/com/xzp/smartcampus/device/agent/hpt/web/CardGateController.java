package com.xzp.smartcampus.device.agent.hpt.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/agent/hpt/card-gate")
@Slf4j
public class CardGateController {


    @PostMapping("/posts")
    public ResponseEntity<Map<String, Object>> postData(@RequestBody Map<String, Object> body,
                                                        @RequestParam(value = "m") String m,
                                                        @RequestParam(value = "a") String a) {

        Map<String, Object> result = new HashMap<>();
        // 心跳
        if ("gateapi".equals(m) && "gatealive".equals(a)) {
            log.info("进入心跳 {}", body);
            result.put("status", "200");
            result.put("errorMessage", "");
            return ResponseEntity.ok(result);
        }
        // 验证
        if ("gateapi".equals(m) && "checkticket".equals(a)) {
            log.info("进入验证 {}", body);
            result.put("checkResult", 1);
            result.put("checkType", 0);
            result.put("VoiceNum", 0);

            result.put("checkMsg1", "test....");
            result.put("checkMsg2", "test...");
            result.put("checkMsg3", "test...");
            result.put("checkMsg4", "test...");
            return ResponseEntity.ok(result);
        }
        // 其他
        log.info("m {} a {} body");

        return ResponseEntity.ok(result);
    }
}
