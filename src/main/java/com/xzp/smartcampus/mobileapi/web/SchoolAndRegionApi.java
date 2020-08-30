package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.model.SchoolModel;
import com.xzp.smartcampus.system.service.IRegionService;
import com.xzp.smartcampus.system.service.ISchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi")
@Slf4j
public class SchoolAndRegionApi {

    @Resource
    private ISchoolService schoolService;

    @Resource
    private IRegionService regionService;

    @GetMapping("/school/gets/page")
    public ResponseEntity<PageResult> getSchoolPage(SchoolModel searchParam,
                                                    @RequestParam(value = "current") Integer current,
                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(schoolService.getSchoolListPage(searchParam, current, pageSize));
    }

    @GetMapping("/region/gets/page")
    public ResponseEntity<PageResult> getRegionPage(RegionModel searchParam,
                                                    @RequestParam(value = "current") Integer current,
                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(regionService.getRegionListPage(searchParam, current, pageSize));
    }

}
