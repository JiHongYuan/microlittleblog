package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.common.utils.crud.BaseCrud;
import com.microlittleblog.common.utils.crud.CrudUtil;
import com.microlittleblog.system.domain.SysRole;
import com.microlittleblog.system.domain.SysRoleMenu;
import com.microlittleblog.system.mapper.SysRoleMapper;
import com.microlittleblog.system.mapper.SysRoleMenuMapper;
import com.microlittleblog.system.service.ISysRoleMenuService;
import com.microlittleblog.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author jihongyuan
 * @date 2020/3/9 18:29
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysRoleMenuService roleMenuService;
    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        return super.list(wrapper);
    }

    @Override
    public SysRole selectRole(SysRole role) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        if (role.getRoleName() != null) {
            wrapper.eq(SysRole::getRoleName, role.getRoleName());
        }

        if (role.getRoleKey() != null) {
            wrapper.eq(SysRole::getRoleKey, role.getRoleKey());
        }
        return super.getOne(wrapper);
    }

    @Override
    public Set<String> selectRoleKeys(Long userId) {
        return null;
    }

    @Override
    public String checkRoleUnique(SysRole role) {
        long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
        SysRole info = this.selectRole(role);
        if (info != null && info.getRoleId() != roleId) {
            return UserConstants.ROLE_NAME_NOT_UNIQUE;
        }
        return UserConstants.ROLE_NAME_UNIQUE;
    }

    @Override
    public int insertRole(SysRole role) {
        List<Long> roleMenuList = role.getMenuIdList();
        if (roleMenuList.size() > 0) {
            List<SysRoleMenu> addList = new ArrayList<>();
            for (Long menuId : roleMenuList) {
                addList.add(new SysRoleMenu(role.getRoleId(), menuId));
            }
            roleMenuService.saveBatch(addList);
        }
        return roleMapper.insert(role);
    }

    @Override
    public int updateRole(SysRole role) {
        List<SysRoleMenu> newList = roleMenuService.list(
                Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, role.getMenuIdList()));

        CrudUtil.updateCollectionHelper(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, role.getRoleId()),
                newList, roleMenuService, SysRoleMenu.class, new BaseCrud<SysRoleMenu>() {
            @Override
            public void before(SysRoleMenu sysRoleMenu) {
                sysRoleMenu.setRoleId(role.getRoleId());
            }
        });
        return roleMapper.updateById(role);
    }

}