package com.xzp.smartcampus.access_strategy.web;

import com.xzp.smartcampus.access_strategy.model.AccessStrategyControlModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyControlService;
import com.xzp.smartcampus.common.exception.SipException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/strategy-control")
@Slf4j
public class AccessStrategyCtrlController {
    @Resource
    IAccessStrategyControlService controlService;

    /**
     * @param controlModel
     * @return
     */
    @PostMapping("/posts")
    public ResponseEntity<String> createStrategyControl(@RequestBody AccessStrategyControlModel controlModel) {
        this.controlService.insert(controlModel);
        return ResponseEntity.ok("Strategy control created successfully!");
    }

    @DeleteMapping("/deletes")
    public ResponseEntity<String> deleteStrategyControl(@RequestParam(value = "id", required = true) String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("Strategy control id: %s is empty!");
            throw new SipException("Strategy control id: %s is empty!");
        }
        this.controlService.deleteById(id);
        return ResponseEntity.ok("Strategy control deleted successfully!");
    }


}
