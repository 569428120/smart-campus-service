package com.xzp.smartcampus.mobileapi.web;


import com.xzp.smartcampus.mobileapi.service.IPersonnelGroupService;
import com.xzp.smartcampus.mobileapi.vo.PersonnelGroupVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author xuzhipeng
 */
@RestController
@RequestMapping("/mobileApi")
public class PersonnelGroupApi {

    @Resource
    private IPersonnelGroupService personnelGroupService;

    @GetMapping("/organization/gets/gets-by-id")
    public ResponseEntity<List<PersonnelGroupVo>> getPersonnelGroupByNodeId(@RequestParam(value = "nodeId", defaultValue = "root") String nodeId,
                                                                            @RequestParam(value = "withUser", defaultValue = "true") Boolean withUser) {
        return ResponseEntity.ok(personnelGroupService.getPersonnelGroupByNodeId(nodeId, withUser));
    }


    @GetMapping("/top-contact/gets")
    public ResponseEntity<List<PersonnelGroupVo>> getTopContact() {
        // TODO 空着不做处理
        return ResponseEntity.ok(Collections.emptyList());
    }


}
