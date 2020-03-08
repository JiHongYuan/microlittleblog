package com.microlittleblog.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.microlittleblog.common.core.domain.BaseEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表 sys_menu
 *
 * @author ruoyi
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu")
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private String orderNum;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 打开方式：menuItem页签 menuBlank新窗口
     */
    private String target;

    /**
     * 类型:0目录,1菜单,2按钮
     */
    private String menuType;

    /**
     * 菜单状态:0显示,1隐藏
     */
    private String visible;

    /**
     * 权限字符串
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 子菜单
     */
    @Builder.Default
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

}