package com.xzp.smartcampus.access_strategy.web;


import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/access-control/strategy-time-quantum")
public class StrategyTimeQuantumController {

    @Resource
    private IAccessStrategyTimeService timeService;

    /**
     * 更新策略下的 策略-时间段 数据,合并增改
     *
     * @param strategyTimeModel 时间段 Model
     * @return AccessStrategyTimeModel
     */
    @PostMapping("/posts")
    public ResponseEntity<AccessStrategyTimeModel> saveAccessStrategyTime(@RequestBody AccessStrategyTimeModel strategyTimeModel) {
        return ResponseEntity.ok(timeService.saveAccessStrategyTime(strategyTimeModel));
    }

    /**
     * 查询时间段
     *
     * @param strategyId 策略id
     * @return ResponseEntity
     */
    @GetMapping("/gets/gets-by-strategyid")
    public ResponseEntity<List<AccessStrategyTimeModel>> selectStrategyPeriod(@RequestParam(value = "strategyId", required = true) String strategyId) {
        return ResponseEntity.ok(timeService.findStrategyPeriod(strategyId));
    }

    /**
     * 删除时间段(批量)
     *
     * @param ids 时间段id列表
     * @return ResponseEntity<String>
     */
    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteStrategyPeriod(@RequestParam(value = "timeQuantumIds") String ids) {
        timeService.deleteByIds(Arrays.asList(ids.split(",")));
        return ResponseEntity.ok(MessageFormat.format("Strategy time-models :{0} have been deleted", ids));
    }

}
