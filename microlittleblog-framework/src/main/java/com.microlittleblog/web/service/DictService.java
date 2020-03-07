package com.microlittleblog.web.service;

import com.microlittleblog.system.domain.SysDictData;
import com.microlittleblog.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * html调用 thymeleaf 实现字典读取
 *
 * @author ruoyi
 */
@Service("dict")
public class DictService {

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<SysDictData> getType(String dictType) {
        return dictDataService.selectDictDataList(SysDictData.builder().dictType(dictType).build());
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue) {
        SysDictData sysDictData = SysDictData.builder()
                .dictValue(dictValue)
                .dictType(dictType).build();
        sysDictData = dictDataService.selectDictData(sysDictData);
        return sysDictData.getDictLabel();
    }

}
