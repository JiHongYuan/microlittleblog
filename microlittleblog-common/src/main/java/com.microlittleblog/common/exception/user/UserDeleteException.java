package com.microlittleblog.common.exception.user;

import com.microlittleblog.common.constant.MessageConstants;

/**
 * 用户账号已被删除
 *
 * @author ruiyi
 */
public class UserDeleteException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserDeleteException() {
        super(MessageConstants.USER_PASSWORD_DELETE, null);
    }

}