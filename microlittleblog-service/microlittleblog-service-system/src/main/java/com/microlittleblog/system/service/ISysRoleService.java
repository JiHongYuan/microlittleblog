package com.microlittleblog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.system.domain.SysRole;

import java.util.List;

/**
 * 角色业务层
 *
 * @author ruoyi
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据条件查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> selectRoleList(SysRole role);

    /**
     * 根据条件查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    SysRole selectRole(SysRole role);

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    String checkRoleUnique(SysRole role);

    /**
     * 插入角色和角色菜单
     *
     * @param role 角色信息
     * @return 结果
     */
    int insertRole(SysRole role);

    /**
     * 更新角色和角色菜单
     *
     * @param role 角色信息
     * @return 结果
     */
    int updateRole(SysRole role);

    /**
     * 删除角色和角色菜单
     *
     * @param ids 角色信息
     * @return 结果
     */
    int removeRole(String ids);

    /**
     * 批量添加授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds userIds
     * @return 结果
     */
    int insertAuthUsers(Long roleId, String userIds);

    /**
     * 批量删除授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds userIds
     * @return 结果
     */
    int removeAuthUsers(Long roleId, String userIds);
}