package com.microlittleblog.system;

import com.microlittleblog.common.core.controller.BaseController;
import com.microlittleblog.common.core.domain.AjaxResult;
import com.microlittleblog.common.core.page.TableDataInfo;
import com.microlittleblog.system.domain.SysDictData;
import com.microlittleblog.system.service.ISysDictDataService;
import com.microlittleblog.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    private String prefix = "system/dict/data";

    @Autowired
    private ISysDictDataService dictDataService;

    @GetMapping()
    public String dictData() {
        return prefix + "/data";
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add/{dictType}")
    public String add(@PathVariable("dictType") String dictType, ModelMap mmap) {
        mmap.put("dictType", dictType);
        return prefix + "/add";
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictCode}")
    public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
        mmap.put("dict", dictDataService.getById(dictCode));
        return prefix + "/edit";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    /**
     * 新增保存字典类型
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDictData dict) {
        return toAjax(dictDataService.save(insert(dict, ShiroUtils.getLoginName())));
    }

    /**
     * 修改保存字典类型
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Valid SysDictData dict) {
        return toAjax(dictDataService.updateById(update(dict, ShiroUtils.getLoginName())));
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(dictDataService.deleteDictDataByIds(ids));
    }

}