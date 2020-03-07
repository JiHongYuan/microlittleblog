package com.microlittleblog.common.exception.user;

import com.microlittleblog.common.constant.MessageConstants;

/**
 * 用户错误最大次数异常类
 *
 * @author ruoyi
 */
public class UserPasswordRetryLimitExceedException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount) {
        super(MessageConstants.USER_PASSWORD_RETRY_LIMIT_EXCEED, new Object[]{retryLimitCount});
    }
}
