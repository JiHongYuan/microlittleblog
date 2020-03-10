package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.common.core.text.Convert;
import com.microlittleblog.common.utils.crud.BaseCrud;
import com.microlittleblog.common.utils.crud.CrudUtil;
import com.microlittleblog.system.domain.SysRole;
import com.microlittleblog.system.domain.SysRoleMenu;
import com.microlittleblog.system.domain.SysUserRole;
import com.microlittleblog.system.mapper.SysRoleMapper;
import com.microlittleblog.system.mapper.SysRoleMenuMapper;
import com.microlittleblog.system.service.ISysRoleMenuService;
import com.microlittleblog.system.service.ISysRoleService;
import com.microlittleblog.system.service.ISysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ISysUserRoleService userRoleService;
    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotEmpty(role.getRoleName())) {
            wrapper.like(SysRole::getRoleName, role.getRoleName());
        }
        if (StringUtils.isNotEmpty(role.getRoleKey())) {
            wrapper.like(SysRole::getRoleKey, role.getRoleKey());
        }
        if (StringUtils.isNotEmpty(role.getStatus())) {
            wrapper.eq(SysRole::getStatus, role.getStatus());
        }
        return super.list(wrapper);
    }

    @Override
    public SysRole selectRole(SysRole role) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();

        if (role.getRoleId() != null) {
            wrapper.eq(SysRole::getRoleId, role.getRoleId());
        }
        if (role.getRoleName() != null) {
            wrapper.eq(SysRole::getRoleName, role.getRoleName());
        }
        if (role.getRoleKey() != null) {
            wrapper.eq(SysRole::getRoleKey, role.getRoleKey());
        }
        return super.getOne(wrapper);
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
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role) {
        int rows = roleMapper.insert(role);
        insertRoleMenu(role);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(SysRole role) {
        roleMenuService.removeById(role.getRoleId());
        insertRoleMenu(role);
        return roleMapper.updateById(role);
    }

    @Override
    public int removeRole(String ids) {
        List<Long> roleIdList = Arrays.asList(Convert.toLongArray(ids));
        // 删除角色 资源关联
        roleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, roleIdList));
        return roleMapper.deleteBatchIds(roleIdList);
    }

    @Override
    public int insertAuthUsers(Long roleId, String userIds) {
        List<SysUserRole> list = new ArrayList<>();
        for (Long userId : Convert.toLongArray(userIds)) {
            list.add(SysUserRole.builder().userId(userId).roleId(roleId).build());
        }
        return userRoleService.saveBatch(list) ? 1 : 0;
    }

    @Override
    public int removeAuthUsers(Long roleId, String userIds) {
        LambdaQueryWrapper<SysUserRole> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(SysUserRole::getRoleId, roleId)
                .in(SysUserRole::getUserId, Arrays.asList(Convert.toLongArray(userIds)));
        return userRoleService.remove(wrapper) ? 1 : 0;
    }

    private void insertRoleMenu(SysRole role) {
        List<Long> roleMenuList = role.getMenuIdList();
        // 添加角色 资源关联
        if (roleMenuList.size() > 0) {
            List<SysRoleMenu> addList = new ArrayList<>();
            for (Long menuId : roleMenuList) {
                addList.add(new SysRoleMenu(role.getRoleId(), menuId));
            }
            roleMenuService.saveBatch(addList);
        }
    }

}