package com.microlittleblog.shiro.service;

import com.microlittleblog.common.constant.Constants;
import com.microlittleblog.common.constant.MessageConstants;
import com.microlittleblog.common.constant.ShiroConstants;
import com.microlittleblog.common.exception.user.UserPasswordNotMatchException;
import com.microlittleblog.common.exception.user.UserPasswordRetryLimitExceedException;
import com.microlittleblog.common.utils.DateUtils;
import com.microlittleblog.common.utils.MessageUtils;
import com.microlittleblog.common.utils.redis.RedisUtils;
import com.microlittleblog.manager.AsyncManager;
import com.microlittleblog.manager.factory.AsyncFactory;
import com.microlittleblog.system.domain.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录密码方法
 *
 * @author ruoyi
 */
@Component
public class SysPasswordService {
    private static final String _RETRY_COUNT = "_RetryCount";

    @Resource
    private RedisUtils redisUtils;

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Long lockTime;

    public void validate(SysUser user, String password) {
        String loginName = user.getLoginName();

        String key = loginName + _RETRY_COUNT;
        AtomicInteger retryCount = redisUtils.get(key, AtomicInteger.class);

        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            redisUtils.set(key, retryCount, lockTime);
        }

        if (retryCount.incrementAndGet() > maxRetryCount) {
            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(loginName, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.USER_PASSWORD_RETRY_LIMIT_EXCEED, maxRetryCount, DateUtils.getSecondToMinute(lockTime))));
            throw new UserPasswordRetryLimitExceedException(maxRetryCount);
        }

        if (!matches(user, password)) {
            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(loginName, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.USER_PASSWORD_RETRY_LIMIT_COUNT, retryCount)));
            redisUtils.set(key, retryCount, lockTime);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(SysUser user, String password) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), password, user.getSalt()));
    }

    private void clearLoginRecordCache(String username) {
        redisUtils.delete(username + _RETRY_COUNT);
    }

    public String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex();
    }

}