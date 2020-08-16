package com.xzp.smartcampus.mobileapi.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.mobileapi.vo.AppMenuInfo;
import com.xzp.smartcampus.system.enums.MenuType;
import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.service.IMobileMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi/menu-authority")
@Slf4j
public class MenuAuthorityApi {

    @Resource
    private IMobileMenuService mobileMenuService;

    @GetMapping("/gets/gets-by-appId")
    public ResponseEntity<List<AppMenuInfo>> getAppMenuInfoListByAppId(@RequestParam(value = "appId") String appId) {
        if (StringUtils.isBlank(appId)) {
            log.warn("appId is null");
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<MenuModel> menuModels = mobileMenuService.selectList(new QueryWrapper<MenuModel>()
                .eq("app_type", MenuType.MOBILE_MENU.getKey())
                .eq("menu_level", 3)
                .eq("pid", appId)
        );
        if (CollectionUtils.isEmpty(menuModels)) {
            log.info("not find menuModels by pid {}", appId);
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(menuModels.stream().map(item -> {
            AppMenuInfo info = new AppMenuInfo();
            BeanUtils.copyProperties(item, info);
            return info;
        }).collect(Collectors.toList()));
    }
}
