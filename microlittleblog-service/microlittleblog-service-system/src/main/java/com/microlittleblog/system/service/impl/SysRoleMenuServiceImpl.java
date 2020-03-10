package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.system.domain.SysRoleMenu;
import com.microlittleblog.system.mapper.SysRoleMenuMapper;
import com.microlittleblog.system.service.ISysRoleMenuService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jihongyuan
 * @date 2020/3/10 14:37
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
}