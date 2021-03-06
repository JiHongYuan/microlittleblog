package com.microlittleblog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.system.domain.SysUserOnline;

import java.util.Date;
import java.util.List;

/**
 * 在线用户 服务层
 *
 * @author ruoyi
 */
public interface ISysUserOnlineService extends IService<SysUserOnline> {

    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return SysUserOnline
     */
    SysUserOnline selectOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     */
    void deleteOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessions 会话ID集合
     */
    void batchDeleteOnline(List<String> sessions);

    /**
     * 查询会话集合
     *
     * @param userOnline 分页参数
     * @return 会话集合
     */
    List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

    /**
     * 强退用户
     *
     * @param sessionId 会话ID
     */
    void forceLogout(String sessionId);

    /**
     * 查询会话集合
     *
     * @param expiredDate 有效期
     * @return List<SysUserOnline>
     */
    public List<SysUserOnline> selectOnlineByExpired(Date expiredDate);

}