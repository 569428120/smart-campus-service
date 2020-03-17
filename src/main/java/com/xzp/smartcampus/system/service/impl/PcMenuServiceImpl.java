package com.xzp.smartcampus.system.service.impl;

import com.xzp.smartcampus.system.enums.MenuType;
import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.service.IPcMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PcMenuServiceImpl extends BaseMenuService implements IPcMenuService {
    /**
     * 获取菜单类型
     *
     * @return String
     */
    @Override
    protected String getMenuType() {
        return MenuType.PC_MENU.getKey();
    }
}
