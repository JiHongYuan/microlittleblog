package com.microlittleblog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.system.domain.SysDictType;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author ruoyi
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return SysDictType
     */
    List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * 根据条件查询字典类型
     *
     * @param dictType dictType
     * @return SysDictType
     */
    SysDictType selectDictType(SysDictType dictType);

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     * @throws Exception 异常
     */
    int deleteDictTypeByIds(String ids) throws Exception;

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    int insertDictType(SysDictType dictType);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    int updateDictType(SysDictType dictType);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    String checkDictTypeUnique(SysDictType dictType);

}