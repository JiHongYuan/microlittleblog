package com.microlittleblog.common.exception.user;

import com.microlittleblog.common.constant.MessageConstants;

/**
 * 用户不存在异常类
 *
 * @author rouyi
 */
public class UserNotExistsException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super(MessageConstants.USER_NOT_EXISTS, null);
    }
}
