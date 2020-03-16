package com.xzp.smartcampus.system.service.impl;

import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.service.IPcMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PcMenuService extends BaseMenuService implements IPcMenuService {
}
