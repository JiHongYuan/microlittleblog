package com.microlittleblog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microlittleblog.system.domain.SysUser;

import java.util.List;

/**
 * @author jihongyuan
 * @date 2020/2/29 18:08
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

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

    /**
     * 查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return List<SysUser>
     */
    List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return List<SysUser>
     */
    List<SysUser> selectUnallocatedList(SysUser user);

}