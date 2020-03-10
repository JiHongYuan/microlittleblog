package com.microlittleblog.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.common.core.domain.Ztree;
import com.microlittleblog.system.domain.SysMenu;
import com.microlittleblog.system.domain.SysRole;
import com.microlittleblog.system.domain.SysUser;

import java.util.List;

/**
 * 菜单 业务层
 *
 * @author ruoyi
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询用户菜单
     *
     * @param user 用户信息
     * @return 用户菜单
     */
    List<SysMenu> selectMenuListByUser(SysUser user);

    /**
     * 查询菜单列表
     *
     * @param sysMenu sysMenu
     * @return 所有菜单
     */
    List<SysMenu> selectMenuList(SysMenu sysMenu);

    /**
     * 根据角色ID查询菜单
     *
     * @return 菜单列表
     */
    List<Ztree> selectRoleMenuTreeList(Long roleId);

}