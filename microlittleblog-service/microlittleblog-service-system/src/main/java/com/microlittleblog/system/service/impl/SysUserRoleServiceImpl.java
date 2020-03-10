package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.system.domain.SysUserRole;
import com.microlittleblog.system.mapper.SysUserRoleMapper;
import com.microlittleblog.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author jihongyuan
 * @date 2020/3/10 21:18
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
}