package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.common.core.domain.Ztree;
import com.microlittleblog.system.domain.SysMenu;
import com.microlittleblog.system.domain.SysUser;
import com.microlittleblog.system.mapper.SysMenuMapper;
import com.microlittleblog.system.service.ISysMenuService;
import com.microlittleblog.system.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * 菜单 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenu> selectMenuListByUser(SysUser user) {
        List<SysMenu> menuList = menuMapper.selectMenuListByUserId(user.getUserId());
        return getChildPerms(menuList);
    }

    @Override
    public List<SysMenu> selectMenuList(SysMenu sysMenu) {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        return super.list(wrapper);
    }

    @Override
    public List<Ztree> selectRoleMenuTreeList(Long roleId) {
        List<Ztree> ztrees = new ArrayList<>();
        List<SysMenu> menuList = this.selectMenuList(new SysMenu());

        List<SysMenu> roleMenuList = null;
        if (roleId != null) {
            roleMenuList = menuMapper.selectMenuListByRoleId(roleId);
        }

        for (SysMenu menu : menuList) {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(menu.getMenuName() + "<font color='#888'>&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
            ztree.setTitle(menu.getMenuName());

            // 编辑选中
            if (roleId != null) {
                ztree.setChecked(roleMenuList.contains(menu));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
     * 遍历根节点
     */
    private List<SysMenu> getChildPerms(List<SysMenu> allMenuList) {
        List<SysMenu> rootList = new ArrayList<>();
        for (Iterator<SysMenu> lt = allMenuList.iterator(); lt.hasNext(); ) {
            SysMenu menu = lt.next();
            if (menu.getParentId() == 0) {
                rootList.add(menu);
                lt.remove();
            }
        }

        for (SysMenu menu : rootList) {
            menu.setChildren(getChild(allMenuList, menu.getMenuId()));
        }
        return rootList;
    }

    /**
     * 递归子节点
     */
    private List<SysMenu> getChild(List<SysMenu> allMenuList, Long id) {
        List<SysMenu> childList = new ArrayList<>();

        for (Iterator<SysMenu> lt = allMenuList.iterator(); lt.hasNext(); ) {
            SysMenu menu = lt.next();
            if (menu.getParentId().equals(id)) {
                childList.add(menu);
                lt.remove();
            }
        }

        for (SysMenu menu : childList) {
            menu.setChildren(getChild(allMenuList, menu.getMenuId()));
        }
        return childList;
    }

}