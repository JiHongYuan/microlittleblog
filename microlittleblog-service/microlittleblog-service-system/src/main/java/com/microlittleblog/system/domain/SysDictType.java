package com.microlittleblog.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.microlittleblog.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典类型表 sys_dict_type
 *
 * @author ruoyi
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dict_type")
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    private Long dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

}