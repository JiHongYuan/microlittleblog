package com.microlittleblog.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.system.domain.SysDictData;
import com.microlittleblog.system.domain.SysLoginInfo;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author ruoyi
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictData 字典数据
     * @return 字典数据
     */
    SysDictData selectDictData(SysDictData dictData);

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteDictDataByIds(String ids);

}