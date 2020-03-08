package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.system.domain.SysMenu;
import com.microlittleblog.system.domain.SysUser;
import com.microlittleblog.system.mapper.SysMenuMapper;
import com.microlittleblog.system.service.ISysMenuService;
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
    public List<SysMenu> selectMenusByUser(SysUser user) {
        List<SysMenu> menuList = menuMapper.selectMenusByUserId(user.getUserId());
        return getChildPerms(menuList);
    }

    @Override
    public int insertMenu(SysMenu menu) {
        menu.setCreateTime(new Date());
        return menuMapper.insert(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        menu.setUpdateTime(new Date());
        return menuMapper.updateById(menu);
    }

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