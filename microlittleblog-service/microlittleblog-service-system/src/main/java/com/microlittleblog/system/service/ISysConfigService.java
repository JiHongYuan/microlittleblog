package com.microlittleblog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.system.domain.SysConfig;

import java.util.List;

/**
 * 参数配置 服务层
 *
 * @author ruoyi
 */
public interface ISysConfigService extends IService<SysConfig> {
    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return SysConfig
     */
    SysConfig selectConfigById(Long configId);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return SysConfig
     */
    String selectConfigByKey(String configKey);

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return SysConfig
     */
    List<SysConfig> selectConfigList(SysConfig config);

    /**
     * 批量删除参数配置信息
     *
     * @param ids 需要删除的数据ID
     * @return int
     */
    int deleteConfigByIds(String ids);

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数信息
     * @return int
     */
    String checkConfigKeyUnique(SysConfig config);

}
