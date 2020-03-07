package com.microlittleblog.common.exception.user;

import com.microlittleblog.common.constant.MessageConstants;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author ruoyi
 */
public class UserPasswordNotMatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super(MessageConstants.USER_PASSWORD_NOT_MATCH, null);
    }

}
