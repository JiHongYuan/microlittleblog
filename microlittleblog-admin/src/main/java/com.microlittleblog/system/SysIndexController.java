package com.microlittleblog.system;

import com.microlittleblog.common.constant.MessageConstants;
import com.microlittleblog.common.core.controller.BaseController;
import com.microlittleblog.common.utils.MessageUtils;
import com.microlittleblog.system.domain.SysMenu;
import com.microlittleblog.system.domain.SysUser;
import com.microlittleblog.system.service.ISysMenuService;
import com.microlittleblog.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class SysIndexController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 系统首页
     */
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        List<SysMenu> menus = menuService.selectMenusByUser(user);

        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", MessageUtils.message(MessageConstants.PROJECT_COPYRIGHT_YEAR));
        mmap.put("projectName", MessageUtils.message(MessageConstants.PROJECT_NAME));
        return "index";
    }

    /**
     * 系统介绍
     */
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", MessageUtils.message(MessageConstants.PROJECT_VERSION));
        return "main";
    }

}
