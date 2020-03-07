package com.microlittleblog.shiro.service;

import com.microlittleblog.shiro.session.OnlineSession;
import com.microlittleblog.system.domain.SysUserOnline;
import com.microlittleblog.system.service.ISysUserOnlineService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会话db操作处理
 *
 * @author ruoyi
 */
@Component
public class SysShiroService {
    @Autowired
    private ISysUserOnlineService onlineService;

    /**
     * 删除会话
     *
     * @param onlineSession 会话信息
     */
    public void deleteSession(OnlineSession onlineSession) {
        onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
    }

    /**
     * 获取会话信息
     *
     * @param sessionId sessionId
     * @return Session
     */
    public Session getSession(Serializable sessionId) {
        SysUserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
        return userOnline == null ? null : createSession(userOnline);
    }

    public Session createSession(SysUserOnline userOnline) {
        OnlineSession onlineSession = new OnlineSession();
        if (userOnline != null) {
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setHost(userOnline.getIpAddress());
            onlineSession.setBrowser(userOnline.getBrowser());
            onlineSession.setOs(userOnline.getOs());
            onlineSession.setLoginName(userOnline.getLoginName());
            onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
            onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        return onlineSession;
    }
}
