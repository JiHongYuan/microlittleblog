package com.microlittleblog.system;

import com.microlittleblog.common.core.controller.BaseController;
import com.microlittleblog.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class SysIndexController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

}
