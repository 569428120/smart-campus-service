package com.xzp.smartcampus.device.gate.web;

import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.device.gate.service.IGateTestService;
import com.xzp.smartcampus.device.gate.vo.TestLogVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/device-manage/gate-test")
public class GateTestController {

    @Resource
    private IGateTestService gateTestService;

    @PostMapping("/start")
    public ResponseEntity<TestLogVo> startTest(@RequestBody GateModel gateModel) {
        return ResponseEntity.ok(gateTestService.startTest(gateModel));
    }

    @GetMapping("/gets/gets-by-deviceId")
    public ResponseEntity<TestLogVo> getGateListPage(@RequestParam(value = "deviceId") String deviceId) {
        return ResponseEntity.ok(gateTestService.getTestLogVoByDeviceId(deviceId));
    }
}
