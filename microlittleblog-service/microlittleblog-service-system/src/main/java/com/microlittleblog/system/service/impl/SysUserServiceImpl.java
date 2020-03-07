package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.system.domain.SysUser;
import com.microlittleblog.system.mapper.SysUserMapper;
import com.microlittleblog.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author jihongyuan
 * @date 2020/2/29 18:14
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser selectUser(SysUser user) {
        return null;
    }

    @Override
    public SysUser selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    @Override
    public SysUser selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public boolean insertUser(SysUser user) {
        user.setCreateTime(new Date());
        return super.save(user);
    }

    @Override
    public boolean updateUser(SysUser user) {
        user.setUpdateTime(new Date());
        return super.updateById(user);
    }
}
