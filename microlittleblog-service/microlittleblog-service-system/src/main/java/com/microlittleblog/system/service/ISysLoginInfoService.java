package com.microlittleblog.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.system.domain.SysDictType;
import com.microlittleblog.system.domain.SysLoginInfo;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author ruoyi
 */
public interface ISysLoginInfoService extends IService<SysLoginInfo> {

    /**
     * 新增保存字典类型信息
     *
     * @param loginInfo loginInfo
     * @return int
     */
    int insertLoginInfo(SysLoginInfo loginInfo);

    /**
     * 修改保存字典类型信息
     *
     * @param loginInfo loginInfo
     * @return int
     */
    int updateLoginInfo(SysLoginInfo loginInfo);
}
