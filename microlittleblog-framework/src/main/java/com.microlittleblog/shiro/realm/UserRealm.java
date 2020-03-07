package com.microlittleblog.shiro.realm;

import com.microlittleblog.common.exception.user.*;
import com.microlittleblog.shiro.service.SysLoginService;
import com.microlittleblog.system.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author ruoyi
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysLoginService loginService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        return null;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = null;
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        SysUser user;
        try {
            user = loginService.login(username, password);
        } catch (CaptchaException e) {
            e.printStackTrace();
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            e.printStackTrace();
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            e.printStackTrace();
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            e.printStackTrace();
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
