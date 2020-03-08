package com.microlittleblog.system;

import com.microlittleblog.common.core.controller.BaseController;
import com.microlittleblog.system.domain.SysMenu;
import com.microlittleblog.system.service.ISysMenuService;
import com.microlittleblog.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    private String prefix = "system/menu";

    @Autowired
    private ISysMenuService menuService;

    @GetMapping()
    public String menu() {
        return prefix + "/menu";
    }

    @PostMapping("/list")
    @ResponseBody
    public List<SysMenu> list(SysMenu menu) {
        return menuService.selectMenuList(menu);
    }

}