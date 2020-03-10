package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.common.core.text.Convert;
import com.microlittleblog.system.domain.SysConfig;
import com.microlittleblog.system.mapper.SysConfigMapper;
import com.microlittleblog.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 参数配置 服务层实现
 *
 * @author ruoyi
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {
    @Autowired
    private SysConfigMapper configMapper;

    @Override
    public SysConfig selectConfigById(Long configId) {
        return configMapper.selectOne(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getConfigId, configId));
    }

    @Override
    public String selectConfigByKey(String configKey) {
        SysConfig config = configMapper.selectOne(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getConfigKey, configKey));
        return config != null ? config.getConfigKey() : "";
    }

    @Override
    public List<SysConfig> selectConfigList(SysConfig config) {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        return configMapper.selectList(wrapper);
    }

    @Override
    public int deleteConfigByIds(String ids) {
        return configMapper.deleteBatchIds(Arrays.asList(Convert.toStrArray(ids)));
    }

    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        long configId = config.getConfigId() == null ? -1L : config.getConfigId();

        SysConfig info = configMapper.selectOne(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getConfigKey, config.getConfigKey()));
        if (info != null && info.getConfigId() != configId) {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }
}