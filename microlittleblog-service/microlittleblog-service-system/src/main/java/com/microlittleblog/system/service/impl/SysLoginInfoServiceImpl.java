package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.system.domain.SysDictType;
import com.microlittleblog.system.domain.SysLoginInfo;
import com.microlittleblog.system.mapper.SysDictTypeMapper;
import com.microlittleblog.system.mapper.SysLoginInfoMapper;
import com.microlittleblog.system.service.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfo> implements ISysLoginInfoService {

    @Autowired
    private SysLoginInfoMapper loginInfoMapper;

    @Override
    public int updateLoginInfo(SysLoginInfo loginInfo) {
        return loginInfoMapper.updateById(loginInfo);
    }

}