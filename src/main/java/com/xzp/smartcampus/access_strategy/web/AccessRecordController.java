package com.xzp.smartcampus.access_strategy.web;

import com.xzp.smartcampus.access_strategy.service.ICarAccessRecordService;
import com.xzp.smartcampus.access_strategy.service.IPersonalAccessRecordService;
import com.xzp.smartcampus.access_strategy.vo.CarAccessSearchParam;
import com.xzp.smartcampus.access_strategy.vo.PersonalAccessSearchParam;
import com.xzp.smartcampus.common.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/access-control")
public class AccessRecordController {
    @Resource
    IPersonalAccessRecordService personalRecordService;
    @Resource
    ICarAccessRecordService carRecordService;

    /**
     * 查询人员出入记录
     * @param param  查询参数(人员姓名/证件/人员类型/策略类型/出入类型/验证方式)
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("personnel-record/gets/page")
    public ResponseEntity<PageResult> searchPersonalAccessRecord(
            PersonalAccessSearchParam param,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {

        return ResponseEntity.ok(this.personalRecordService.selectPersonalAccessRecord(param,current,pageSize));
    }

    /**
     * 查询车辆出入记录
     * @param param  查询参数(人员姓名/证件/人员类型/策略类型/出入类型)
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("car-record/gets/page")
    public ResponseEntity<PageResult> searchCarAccessRecord(
            CarAccessSearchParam param,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {

        return ResponseEntity.ok(this.carRecordService.selectCarAccessRecord(param,current,pageSize));
    }
}
