//package com.microlittleblog.system;
//
//import com.microlittleblog.common.constant.UserConstants;
//import com.microlittleblog.common.core.controller.BaseController;
//import com.microlittleblog.common.core.domain.AjaxResult;
//import com.microlittleblog.shiro.service.SysPasswordService;
//import com.microlittleblog.system.domain.SysUser;
//import com.microlittleblog.system.service.ISysUserService;
//import com.microlittleblog.util.ShiroUtils;
//import lombok.extern.java.Log;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
///**
// * 用户信息
// *
// * @author ruoyi
// */
//@Controller
//@RequestMapping("/system/user")
//public class SysUserController extends BaseController {
//    private String prefix = "system/user";
//
//    @Autowired
//    private ISysUserService userService;
//    @Autowired
//    private SysPasswordService passwordService;
//    /**
//     * 新增用户
//     */
//    @PostMapping("/add")
//    @ResponseBody
//    public AjaxResult addSave(@Valid SysUser user) {
//        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName()))) {
//            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
//        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
//            return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
//        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
//            return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
//        }
//        user.setSalt(ShiroUtils.randomSalt());
//        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
//        user.setCreateBy(ShiroUtils.getLoginName());
//        return toAjax(userService.insertUser(user));
//    }
//
//}