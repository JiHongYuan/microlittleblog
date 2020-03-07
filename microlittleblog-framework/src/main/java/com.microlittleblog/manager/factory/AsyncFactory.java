package com.microlittleblog.manager.factory;

import com.microlittleblog.common.constant.Constants;
import com.microlittleblog.common.utils.AddressUtils;
import com.microlittleblog.common.utils.LogUtils;
import com.microlittleblog.common.utils.ServletUtils;
import com.microlittleblog.common.utils.spring.SpringUtils;
import com.microlittleblog.shiro.session.OnlineSession;
import com.microlittleblog.system.domain.SysLoginInfo;
import com.microlittleblog.system.service.impl.SysLoginInfoServiceImpl;
import com.microlittleblog.util.ShiroUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author ruoyi
 */
public class AsyncFactory {
    private static final Logger logger = LoggerFactory.getLogger("sys-user");

    /**
     * 同步session到数据库
     *
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session) {
        return null;
    }


    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static Runnable recordLogin(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();

        return (() -> {
            String address = AddressUtils.getRealAddressByIP(ip);

            LogUtils.info(logger, LogUtils.getBlock(ip), address, LogUtils.getBlock(username), LogUtils.getBlock(status), LogUtils.getBlock(message));

            // 获取客户端操作系统
            String os = userAgent.getOperatingSystem().getName();
            // 获取客户端浏览器
            String browser = userAgent.getBrowser().getName();
            // 封装对象
            SysLoginInfo loginInfo = SysLoginInfo.builder()
                    .loginName(username)
                    .ipAddress(ip)
                    .loginLocation(address)
                    .browser(browser)
                    .os(os)
                    .msg(message)
                    .build();
            // 日志状态
            if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
                loginInfo.setStatus(Constants.SUCCESS);
            } else if (Constants.LOGIN_FAIL.equals(status)) {
                loginInfo.setStatus(Constants.FAIL);
            }
            // 插入数据
            SpringUtils.getBean(SysLoginInfoServiceImpl.class).insertLoginInfo(loginInfo);
        });
    }

}