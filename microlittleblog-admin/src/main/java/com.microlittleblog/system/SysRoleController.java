package com.microlittleblog.system;

import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.common.core.controller.BaseController;
import com.microlittleblog.common.core.domain.AjaxResult;
import com.microlittleblog.common.core.page.TableDataInfo;
import com.microlittleblog.common.core.text.Convert;
import com.microlittleblog.system.domain.SysRole;
import com.microlittleblog.system.domain.SysUser;
import com.microlittleblog.system.service.ISysRoleService;
import com.microlittleblog.system.service.ISysUserService;
import com.microlittleblog.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 角色信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    private String prefix = "system/role";

    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysUserService userService;

    @GetMapping()
    public String role() {
        return prefix + "/role";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysRole role) {
        startPage();
        List<SysRole> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/edit";
    }

    /**
     * 授权用户
     */
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/authUser";
    }

    /**
     * 选择用户
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/selectUser";
    }

    /**
     * 新增保存角色
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Valid SysRole role) {
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleUnique(SysRole.builder().roleName(role.getRoleName()).build()))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleUnique(SysRole.builder().roleKey(role.getRoleKey()).build()))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(roleService.insertRole(insert(role, ShiroUtils.getLoginName())));
    }

    /**
     * 修改保存角色
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Valid SysRole role) {
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleUnique(
                SysRole.builder().roleName(role.getRoleName()).roleId(role.getRoleId()).build()))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");

        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleUnique(
                SysRole.builder().roleKey(role.getRoleKey()).roleId(role.getRoleId()).build()))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }

        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(roleService.updateRole(update(role, ShiroUtils.getLoginName())));
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(roleService.removeByIds(Arrays.asList(Convert.toLongArray(ids))));
    }

    /**
     * 校验角色
     */
    @PostMapping("/checkRoleUnique")
    @ResponseBody
    public String checkRoleUnique(SysRole role) {
        return roleService.checkRoleUnique(role);
    }

    /**
     * 角色状态修改
     */
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysRole role) {
        role = SysRole.builder()
                .roleId(role.getRoleId())
                .status(role.getStatus()).build();
        return toAjax(roleService.updateById(role));
    }

    /**
     * 查询已分配用户角色列表
     */
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableDataInfo allocatedList(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectAllocatedList(user);
        return getDataTable(list);
    }

    /**
     * 查询未分配用户角色列表
     */
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableDataInfo unallocatedList(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUnallocatedList(user);
        return getDataTable(list);
    }

    /**
     * 批量添加用户授权
     */
    @PostMapping("/authUser/addUsers")
    @ResponseBody
    public AjaxResult authUserAddUsers(Long roleId, String userIds) {
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }

    /**
     * 删除用户授权
     */
    @PostMapping("/authUser/removeUser")
    @ResponseBody
    public AjaxResult authUserRemoveUser(Long roleId, Long userId) {
        return toAjax(roleService.removeAuthUsers(roleId, String.valueOf(userId)));
    }

    /**
     * 批量删除用户授权
     */
    @PostMapping("/authUser/removeUsers")
    @ResponseBody
    public AjaxResult authUserRemoveUsers(Long roleId, String userIds) {
        return toAjax(roleService.removeAuthUsers(roleId, userIds));
    }

}