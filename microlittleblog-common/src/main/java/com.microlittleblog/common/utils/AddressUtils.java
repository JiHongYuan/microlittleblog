package com.microlittleblog.common.utils;

import com.microlittleblog.common.utils.http.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    private static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";

        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }

        return "";
    }
}
