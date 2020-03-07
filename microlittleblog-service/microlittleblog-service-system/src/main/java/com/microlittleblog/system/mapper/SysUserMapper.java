package com.microlittleblog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microlittleblog.system.domain.SysUser;

/**
 * @author jihongyuan
 * @date 2020/2/29 18:08
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户信息
     *
     * @param user userInfo
     * @return SysUser
     */
    SysUser selectUser(SysUser user);

    /**
     * 通过名次查询用户
     *
     * @param userName 用户名
     * @return SysUser
     */
    SysUser selectUserByLoginName(String userName);

    /**
     * 通过邮件查询用户
     *
     * @param email 右键
     * @return SysUser
     */
    SysUser selectUserByEmail(String email);

}