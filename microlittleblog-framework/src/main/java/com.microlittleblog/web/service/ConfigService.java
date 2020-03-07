package com.microlittleblog.web.service;

import com.microlittleblog.system.service.ISysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * html调用 thymeleaf 实现参数管理
 *
 * @author ruoyi
 */
@Service("config")
public class ConfigService {
    @Resource
    private ISysConfigService configService;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    public String getKey(String configKey) {
        return configService.selectConfigByKey(configKey);
    }

}