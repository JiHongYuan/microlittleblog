package com.microlittleblog.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.system.domain.SysMenu;
import com.microlittleblog.system.domain.SysUser;

import java.util.List;

/**
 * 菜单 业务层
 *
 * @author ruoyi
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 新增保存菜单信息
     *
     * @param user 用户信息
     * @return List<SysMenu>
     */
    List<SysMenu> selectMenusByUser(SysUser user);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int insertMenu(SysMenu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int updateMenu(SysMenu menu);

}