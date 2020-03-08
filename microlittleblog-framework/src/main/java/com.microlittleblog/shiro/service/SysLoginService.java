package com.microlittleblog.shiro.service;

import com.microlittleblog.common.constant.Constants;
import com.microlittleblog.common.constant.MessageConstants;
import com.microlittleblog.common.constant.ShiroConstants;
import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.common.enums.DeleteStatus;
import com.microlittleblog.common.exception.user.*;
import com.microlittleblog.common.utils.MessageUtils;
import com.microlittleblog.common.utils.ServletUtils;
import com.microlittleblog.manager.AsyncManager;
import com.microlittleblog.manager.factory.AsyncFactory;
import com.microlittleblog.system.domain.SysUser;
import com.microlittleblog.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysPasswordService passwordService;

    @Resource
    private HttpServletResponse response;

    /**
     * 登录
     */
    public SysUser login(String username, String password) {

        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(username, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.NOT_NULL)));
            throw new UserNotExistsException();
        }

//        // 验证码校验
//        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
//            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(username, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.USER_JCAPTCHA_ERROR)));
//            throw new CaptchaException();
//        }

        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(username, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.USER_PASSWORD_NOT_MATCH)));
            throw new UserPasswordNotMatchException();
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(username, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.USER_PASSWORD_NOT_MATCH)));
            throw new UserPasswordNotMatchException();
        }

        // 查询用户信息
        SysUser user = userService.selectUserByLoginName(username);

        if (user == null && maybeEmail(username)) {
            user = userService.selectUserByEmail(username);
        }

        if (user == null) {
            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(username, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.USER_NOT_EXISTS)));
            throw new UserNotExistsException();
        }

        if (DeleteStatus.DELETED.getCode().equals(user.getDeleted())) {
            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(username, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.USER_PASSWORD_DELETE)));
            throw new UserDeleteException();
        }

        if (DeleteStatus.DISABLE.getCode().equals(user.getStatus())) {
            AsyncManager.getInstance().execute(AsyncFactory.recordLogin(username, Constants.LOGIN_FAIL, MessageUtils.message(MessageConstants.USER_BLOCKED, user.getRemark())));
            throw new UserBlockedException();
        }

        passwordService.validate(user, password);
        AsyncManager.getInstance().execute(AsyncFactory.recordLogin(username, Constants.LOGIN_SUCCESS, MessageUtils.message(MessageConstants.USER_LOGIN_SUCCESS)));
        return user;
    }

    private boolean maybeEmail(String username) {
        return !username.matches(UserConstants.EMAIL_PATTERN);
    }

    private boolean maybeMobilePhoneNumber(String username) {
        return !username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN);
    }

}