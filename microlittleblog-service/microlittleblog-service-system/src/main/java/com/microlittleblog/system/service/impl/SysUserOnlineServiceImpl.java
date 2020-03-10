package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.utils.DateUtils;
import com.microlittleblog.system.domain.SysUser;
import com.microlittleblog.system.domain.SysUserOnline;
import com.microlittleblog.system.mapper.SysUserMapper;
import com.microlittleblog.system.mapper.SysUserOnlineMapper;
import com.microlittleblog.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 在线用户 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserOnlineServiceImpl extends ServiceImpl<SysUserOnlineMapper, SysUserOnline> implements ISysUserOnlineService {
    @Autowired
    private SysUserOnlineMapper userOnlineMapper;

    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineById(String sessionId) {
        return userOnlineMapper.selectOne(Wrappers.<SysUserOnline>lambdaQuery().eq(SysUserOnline::getSessionId, sessionId));
    }

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     */
    @Override
    public void deleteOnlineById(String sessionId) {
        userOnlineMapper.delete(Wrappers.<SysUserOnline>lambdaQuery().eq(SysUserOnline::getSessionId, sessionId));
    }

    /**
     * 通过会话序号删除信息
     *
     * @param sessions 会话ID集合
     */
    @Override
    public void batchDeleteOnline(List<String> sessions) {
        userOnlineMapper.deleteBatchIds(sessions);
    }

    /**
     * 查询会话集合
     *
     * @param userOnline 在线用户
     */
    @Override
    public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline) {
        LambdaQueryWrapper<SysUserOnline> wrapper = Wrappers.lambdaQuery();

        if (userOnline.getIpAddress() != null) {
            wrapper.like(SysUserOnline::getIpAddress, userOnline.getIpAddress());
        }

        if (userOnline.getLoginName() != null) {
            wrapper.like(SysUserOnline::getLoginName, userOnline.getLoginName());
        }

        return userOnlineMapper.selectList(wrapper);
    }

    /**
     * 强退用户
     *
     * @param sessionId 会话ID
     */
    @Override
    public void forceLogout(String sessionId) {
        userOnlineMapper.delete(Wrappers.<SysUserOnline>lambdaQuery().eq(SysUserOnline::getSessionId, sessionId));
    }

    @Override
    public List<SysUserOnline> selectOnlineByExpired(Date expiredDate) {
        String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        return userOnlineMapper.selectList(Wrappers.<SysUserOnline>lambdaQuery()
                .ge(SysUserOnline::getExpireTime, lastAccessTime)
                .orderByDesc(SysUserOnline::getLastAccessTime));
    }

}