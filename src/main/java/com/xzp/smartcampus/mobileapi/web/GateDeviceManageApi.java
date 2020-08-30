package com.xzp.smartcampus.mobileapi.web;


import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.device.gate.service.IGateService;
import com.xzp.smartcampus.device.gate.service.IManufacturerService;
import com.xzp.smartcampus.device.gate.vo.ManufacturerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 门禁设备管理
 *
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi/device-manage/gate")
@Slf4j
public class GateDeviceManageApi {

    @Resource
    private IGateService gateService;

    @Resource
    private IManufacturerService manufacturerService;

    /**
     * 分页查询
     *
     * @param searchParam 搜索条件
     * @param current     当前页
     * @param pageSize    每页显示的数量
     * @return PageResult
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getGateVoPage(GateModel searchParam,
                                                    @RequestParam(value = "current") Integer current,
                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(gateService.getGateListPage(searchParam, current, pageSize));
    }

    /**
     * 获取厂商列表
     *
     * @return 列表
     */
    @GetMapping("/manufacturer/gets")
    public ResponseEntity<List<ManufacturerVo>> getGateManufacturerList() {
        return ResponseEntity.ok(manufacturerService.getGateManufacturerVoList());
    }

    /**
     * 设备网络测试
     *
     * @param gateId 设备id
     * @return String
     * @throws InterruptedException 异常
     */
    @GetMapping("/gets/net-test")
    public ResponseEntity<String> netTest(String gateId) throws InterruptedException {
        if (StringUtils.isBlank(gateId)) {
            log.warn("gateId is null");
            throw new SipException("参数错误，gateId为空");
        }
        Thread.sleep(3000);
        return ResponseEntity.ok("ok");
    }

    /**
     * 保存门闸
     *
     * @param gateModel 数据
     * @return String
     */
    @PostMapping("/posts")
    public ResponseEntity<String> saveGateModel(@RequestBody GateModel gateModel) {
        gateService.saveGateModel(gateModel);
        return ResponseEntity.ok("ok");
    }


    /**
     * 删除门禁设备
     *
     * @param gateIds 门禁id
     * @return String
     */
    @DeleteMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteByIds(@RequestParam(value = "gateIds") List<String> gateIds) {
        if (CollectionUtils.isEmpty(gateIds)) {
            log.warn("gateIds is null");
            throw new SipException("参数错误，gateIds为空");
        }
        gateService.deleteByIds(gateIds);
        return ResponseEntity.ok("ok");
    }
}
