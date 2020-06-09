package com.xzp.smartcampus.access_strategy.web;


import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/access-control")
public class AccessStrategyController {
    @Resource
    private IAccessStrategyService strategyService;
    @Resource
    private IAccessStrategyTimeService timeService;

    /**
     * 按条件(名称+状态)搜索策略
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/access-strategy/gets/page")
    public ResponseEntity<PageResult> selectStrategyByCondition(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "current",defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize
    ) {

        return ResponseEntity.ok(this.strategyService.findStrategyByCondition(name, status,current,pageSize));
    }

    /**
     * 新增策略
     * @param strategyModel
     * @return
     */
    @PostMapping("/access-strategy/posts")
    public ResponseEntity<String> addAccessStrategy(@RequestBody AccessStrategyModel strategyModel){
        this.strategyService.insert(strategyModel);
        return ResponseEntity.ok("New access strategy added successful!");
    }

    /**
     * 新增策略对应的时间段(批量)
     * @param timeModels
     * @return
     */
    @PostMapping("/access-strategy/posts/period")
    public ResponseEntity<String> addAccessStrategy(@RequestBody List<AccessStrategyTimeModel> timeModels){
        this.timeService.insertBatch(timeModels);
        return ResponseEntity.ok("New strategy-time added successful!");
    }

    /**
     * 批量删除策略(根据ids)
     * @param strategyIds
     * @return
     */
    @DeleteMapping("/deletes/strategy")
    public ResponseEntity<String> deleteAccessStrategy(@RequestParam(value = "strategyIds",required = true) String strategyIds){
        this.strategyService.deleteStrategyByIds(Arrays.asList(strategyIds.split(",")));
        return ResponseEntity.ok("Strategys deleted successfully");
    }

    /**
     * 更新策略
     * @param strategyModel
     * @return
     */
    @PutMapping("/puts/strategy")
    public ResponseEntity<String> modifyAccessStrategy(@RequestBody AccessStrategyModel strategyModel){
        this.strategyService.updateById(strategyModel);
        return ResponseEntity.ok("Strategy modified successfully");
    }

    /**
     * 更新策略下的 策略-时间段 数据,合并增删改
     * @param strategyTimeModels
     * @return
     */
    @PostMapping("/posts/strategy-time")
    public ResponseEntity<String> modifyAccessStrategyTime(@RequestBody List<AccessStrategyTimeModel> strategyTimeModels){
        if(CollectionUtils.isEmpty(strategyTimeModels)){
            throw new SipException("StrategyTimeModels is null!");
        }
        this.timeService.modifyAccessStrategyTime(strategyTimeModels);

        return ResponseEntity.ok("Strategy-Time modified successfully");
    }


    /**
     * 查询策略对应的时间段
     * @param strategyId 策略id
     * @return
     */
    @GetMapping("/access-strategy/gets/period")
    public ResponseEntity<List> selectStrategyPeriod(@RequestParam(value = "strategyId",required = true) String strategyId){

        return ResponseEntity.ok(this.timeService.findStrategyPeriod(strategyId));
    }


}















