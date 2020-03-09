package com.xzp.smartcampus.access_strategy.web;

import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import com.xzp.smartcampus.common.exception.SipException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/access-strategy-time")
@Slf4j
public class AccessStrategyTimeController {
    @Resource
    IAccessStrategyTimeService strategyTimeService;

    /**
     * 更新策略下的 策略-时间段 数据,合并增删改
     * @param strategyTimeModels
     * @return
     */
    @PostMapping("/posts/strategy-time")
    public ResponseEntity<String> modifyAccessStrategyTime(@RequestBody List<AccessStrategyTimeModel> strategyTimeModels){
        if(CollectionUtils.isEmpty(strategyTimeModels)){
            log.error("List of strategy-time entity is null or empty!");
            throw new SipException("StrategyTimeModels is null!");
        }
        this.strategyTimeService.modifyAccessStrategyTime(strategyTimeModels);

        return ResponseEntity.ok("Strategy-Time modified successfully");
    }























}
