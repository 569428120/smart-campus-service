package com.xzp.smartcampus.device.gate.web;

import com.xzp.smartcampus.device.gate.service.IManufacturerService;
import com.xzp.smartcampus.device.gate.vo.ManufacturerVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/device-manage/manufacturer")
public class ManufacturerController {

    @Resource
    private IManufacturerService manufacturerService;

    @GetMapping("/gets/gets-gate")
    public ResponseEntity<List<ManufacturerVo>> getGateManufacturerList() {
        return ResponseEntity.ok(manufacturerService.getGateManufacturerVoList());
    }
}
