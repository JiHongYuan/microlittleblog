package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.core.text.Convert;
import com.microlittleblog.system.domain.SysDictData;
import com.microlittleblog.system.mapper.SysDictDataMapper;
import com.microlittleblog.system.service.ISysDictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.<SysDictData>lambdaQuery();
        if(StringUtils.isNotEmpty(dictData.getDictType())){
            wrapper.eq(SysDictData::getDictType, dictData.getDictType());
        }
        if (StringUtils.isNotEmpty(dictData.getStatus())) {
            wrapper.like(SysDictData::getStatus, dictData.getStatus());
        }
        if (StringUtils.isNotEmpty(dictData.getDictLabel())) {
            wrapper.like(SysDictData::getDictLabel, dictData.getDictLabel());
        }
        return dictDataMapper.selectList(wrapper);
    }

    @Override
    public SysDictData selectDictData(SysDictData dictData) {
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.lambdaQuery();
        if (dictData.getDictCode() != null) {
            wrapper.eq(SysDictData::getDictCode, dictData.getDictCode());
        }
        if (dictData.getDictType() != null) {
            wrapper.eq(SysDictData::getDictType, dictData.getDictType());
        }
        if (dictData.getDictValue() != null) {
            wrapper.eq(SysDictData::getDictValue, dictData.getDictValue());
        }
        return dictDataMapper.selectOne(wrapper);
    }

    @Override
    public int deleteDictDataByIds(String ids) {
        return dictDataMapper.deleteBatchIds(Arrays.asList(Convert.toStrArray(ids)));
    }

}