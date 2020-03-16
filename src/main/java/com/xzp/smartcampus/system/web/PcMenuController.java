package com.xzp.smartcampus.system.web;

import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.model.SchoolModel;
import com.xzp.smartcampus.system.service.IPcMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * pc段菜单
 */
@RestController
@RequestMapping("/pc-menu")
public class PcMenuController {

    @Resource
    private IPcMenuService pcMenuService;

    /**
     * 查询列表
     *
     * @param searchValue 搜索条件
     * @return ResponseEntity<List < MenuModel>>
     */
    @GetMapping("/gets/all")
    public ResponseEntity<List<MenuModel>> getPcMenuList(MenuModel searchValue) {
        return ResponseEntity.ok(pcMenuService.getMenuList(searchValue));
    }

    /**
     * 删除数据
     *
     * @param menuIds menuIds
     * @return ResponseEntity<String>
     */
    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteSchoolByIds(@RequestParam(value = "menuIds", defaultValue = "") String menuIds) {
        pcMenuService.deleteByIds(Arrays.asList(menuIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }

    /**
     * 修改或新增方法
     *
     * @param menuModel 数据
     * @return ResponseEntity<RegionModel>
     */
    @PostMapping("/posts")
    public ResponseEntity<MenuModel> savePcMenuData(@RequestBody MenuModel menuModel) {
        return ResponseEntity.ok(pcMenuService.saveMenuData(menuModel));
    }
}
