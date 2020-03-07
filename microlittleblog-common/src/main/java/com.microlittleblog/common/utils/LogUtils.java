package com.microlittleblog.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;

/**
 * 处理并记录日志文件
 *
 * @author ruoyi
 */
public class LogUtils {
    public static final Logger ERROR_LOG = LoggerFactory.getLogger("sys-error");
    public static final Logger ACCESS_LOG = LoggerFactory.getLogger("sys-access");


    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }

    public static void error(Throwable e, Logger logger, String... args) {
        logger.error(StringUtils.join(args), e);
    }

    public static void info(Logger logger, String... args) {
        logger.info(StringUtils.join(args));
    }

    public static void debug(Throwable e, Logger logger, String... args) {
        logger.debug(StringUtils.join(args), e);
    }

    public static void warn(Throwable e, Logger logger, String... args) {
        logger.warn(StringUtils.join(args), e);
    }
}