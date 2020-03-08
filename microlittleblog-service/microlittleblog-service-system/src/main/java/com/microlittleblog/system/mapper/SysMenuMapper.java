package com.microlittleblog.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microlittleblog.system.domain.SysMenu;

import java.util.List;

/**
 * 菜单表 数据层
 *
 * @author ruoyi
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByUserId(Long userId);

}