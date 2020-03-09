package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.system.domain.SysRole;
import com.microlittleblog.system.mapper.SysRoleMapper;
import com.microlittleblog.system.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author jihongyuan
 * @date 2020/3/9 18:29
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

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
}
