package com.microlittleblog.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.common.core.text.Convert;
import com.microlittleblog.common.exception.BusinessException;
import com.microlittleblog.system.domain.SysDictData;
import com.microlittleblog.system.domain.SysDictType;
import com.microlittleblog.system.mapper.SysDictDataMapper;
import com.microlittleblog.system.mapper.SysDictTypeMapper;
import com.microlittleblog.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {
    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery();
        return dictTypeMapper.selectList(wrapper);
    }

    @Override
    public SysDictType selectDictType(SysDictType dictType) {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery();

        if (dictType.getDictId() != null) {
            wrapper.eq(SysDictType::getDictId, dictType.getDictId());
        }

        if (dictType.getDictType() != null) {
            wrapper.eq(SysDictType::getDictType, dictType.getDictType());
        }

        return dictTypeMapper.selectOne(wrapper);
    }

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public int deleteDictTypeByIds(String ids) throws BusinessException {
        Long[] dictIds = Convert.toLongArray(ids);
        for (Long dictId : dictIds) {
            SysDictType dictType = this.selectDictType(SysDictType.builder().dictId(dictId).build());
            Wrappers.<SysDictType>lambdaQuery().eq(SysDictType::getDictType, dictType.getDictType());
            if (dictDataMapper.selectCount(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType.getDictType())) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }

        return dictTypeMapper.deleteBatchIds(Arrays.asList(dictIds));
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(SysDictType dictType) {
        dictType.setCreateTime(new Date());
        return dictTypeMapper.insert(dictType);
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDictType(SysDictType dictType) {
        SysDictType oldDict = this.selectDictType(SysDictType.builder().dictId(dictType.getDictId()).build());
        LambdaUpdateWrapper<SysDictData> wrapper = Wrappers.<SysDictData>lambdaUpdate().eq(SysDictData::getDictType, oldDict.getDictType());
        dictDataMapper.update(SysDictData.builder().dictType(oldDict.getDictType()).build(), wrapper);
        return dictTypeMapper.updateById(dictType);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(SysDictType dict) {
        long dictId = dict.getDictId() == null ? -1L : dict.getDictId();
        SysDictType dictType = this.selectDictType(SysDictType.builder().dictType(dict.getDictType()).build());
        if (dictType != null && dictType.getDictId() != dictId) {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
    }


    public String transDictName(SysDictType dictType) {
        return "(" + dictType.getDictName() + ")" +
                "&nbsp;&nbsp;&nbsp;" + dictType.getDictType();
    }

}