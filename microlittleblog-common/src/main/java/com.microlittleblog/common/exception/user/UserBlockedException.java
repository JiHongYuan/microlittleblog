package com.microlittleblog.common.exception.user;

import com.microlittleblog.common.constant.MessageConstants;

/**
 * 用户锁定异常类
 *
 * @author ruoyi
 */
public class UserBlockedException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserBlockedException() {
        super(MessageConstants.USER_BLOCKED, null);
    }
}
