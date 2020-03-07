package com.microlittleblog.shiro.web.session;

import com.microlittleblog.common.constant.ShiroConstants;
import com.microlittleblog.common.utils.bean.BeanUtils;
import com.microlittleblog.common.utils.spring.SpringUtils;
import com.microlittleblog.shiro.session.OnlineSession;
import com.microlittleblog.system.domain.SysUserOnline;
import com.microlittleblog.system.service.ISysUserOnlineService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 主要是在此如果会话的属性修改了 就标识下其修改了 然后方便 OnlineSessionDao同步
 *
 * @author ruoyi
 */
public class OnlineWebSessionManager extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

    @Override
    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) throws InvalidSessionException {
        super.setAttribute(sessionKey, attributeKey, value);
        if (value != null && needMarkAttributeChanged(attributeKey)) {
            OnlineSession session = getOnlineSession(sessionKey);
            session.setAttributeChanged(true);
        }
    }

    private boolean needMarkAttributeChanged(Object attributeKey) {
        if (attributeKey == null) {
            return false;
        }
        String attributeKeyStr = attributeKey.toString();
        // 优化 flash属性没必要持久化
        if (attributeKeyStr.startsWith("org.springframework")) {
            return false;
        }
        if (attributeKeyStr.startsWith("javax.servlet")) {
            return false;
        }

        return !attributeKeyStr.equals(ShiroConstants.CURRENT_USERNAME);
    }

    @Override
    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) throws InvalidSessionException {
        Object removed = super.removeAttribute(sessionKey, attributeKey);
        if (removed != null) {
            OnlineSession session = getOnlineSession(sessionKey);
            session.setAttributeChanged(true);
        }

        return removed;
    }

    public OnlineSession getOnlineSession(SessionKey sessionKey) {
        OnlineSession session = null;
        Object obj = doGetSession(sessionKey);
        if (obj != null) {
            session = new OnlineSession();
            BeanUtils.copyBeanProp(session, obj);
        }
        return session;
    }

    /**
     * 验证session是否有效 用于删除过期session
     */
    @Override
    public void validateSessions() {
        if (log.isInfoEnabled()) {
            log.info("invalidation sessions...");
        }

        int invalidCount = 0;

        int timeout = (int) this.getGlobalSessionTimeout();
        Date expiredDate = DateUtils.addMilliseconds(new Date(), 0 - timeout);
        ISysUserOnlineService userOnlineService = SpringUtils.getBean(ISysUserOnlineService.class);
        List<SysUserOnline> userOnlineList = userOnlineService.selectOnlineByExpired(expiredDate);

        // 批量过期删除
        List<String> needOfflineIdList = new ArrayList<>();
        for (SysUserOnline userOnline : userOnlineList) {
            try {
                SessionKey key = new DefaultSessionKey(userOnline.getSessionId());
                Session session = retrieveSession(key);
                if (session != null) {
                    throw new InvalidSessionException();
                }
            } catch (InvalidSessionException e) {
                if (log.isDebugEnabled()) {
                    boolean expired = (e instanceof ExpiredSessionException);
                    String msg = "Invalidated session with id [" + userOnline.getSessionId() + "]"
                            + (expired ? " (expired)" : " (stopped)");
                    log.debug(msg);
                }
                invalidCount++;
                needOfflineIdList.add(userOnline.getSessionId());
            }

        }
        if (needOfflineIdList.size() > 0) {
            try {
                userOnlineService.batchDeleteOnline(needOfflineIdList);
            } catch (Exception e) {
                log.error("batch delete db session error.", e);
            }
        }

        if (log.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Finished invalidation session.");
            if (invalidCount > 0) {
                sb.append(" [\" + invalidCount + \"] sessions were stopped.");
            } else {
                sb.append(" No sessions were stopped.");
            }
            log.info(sb.toString());
        }

    }

    @Override
    protected Collection<Session> getActiveSessions() {
        throw new UnsupportedOperationException("getActiveSessions method not supported");
    }

}