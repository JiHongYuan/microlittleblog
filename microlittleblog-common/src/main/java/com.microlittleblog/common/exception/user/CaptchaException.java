package com.microlittleblog.common.exception.user;

import com.microlittleblog.common.constant.MessageConstants;

/**
 * 验证码错误异常类
 *
 * @author ruoyi
 */

public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super(MessageConstants.USER_JCAPTCHA_ERROR, null);
    }

}