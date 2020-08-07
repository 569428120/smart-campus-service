package com.xzp.smartcampus.system.service.impl;

import com.xzp.smartcampus.system.enums.MenuType;
import com.xzp.smartcampus.system.service.IMobileMenuService;
import com.xzp.smartcampus.system.service.IPcMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MobileMenuServiceImpl extends BaseMenuService implements IMobileMenuService {
    /**
     * 获取菜单类型
     *
     * @return String
     */
    @Override
    protected String getMenuType() {
        return MenuType.MOBILE_MENU.getKey();
    }

}
