package com.microlittleblog.system;

import com.microlittleblog.common.constant.UserConstants;
import com.microlittleblog.common.core.controller.BaseController;
import com.microlittleblog.common.core.domain.AjaxResult;
import com.microlittleblog.common.core.page.TableDataInfo;
import com.microlittleblog.system.domain.SysDictType;
import com.microlittleblog.system.service.ISysDictTypeService;
import com.microlittleblog.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController {
    private String prefix = "system/dict/type";

    @Autowired
    private ISysDictTypeService dictTypeService;

    @GetMapping()
    public String dictType() {
        return prefix + "/type";
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.getById(dictId));
        return prefix + "/edit";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    /**
     * 新增保存字典类型
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Valid SysDictType dict) {
        if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return error("新增字典'", dict.getDictName(), "'失败，字典类型已存在");
        }
        return toAjax(dictTypeService.save(insert(dict, ShiroUtils.getLoginName())));
    }

    /**
     * 修改保存字典类型
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDictType dict) {
        if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return error("修改字典'", dict.getDictName(), "'失败，字典类型已存在");
        }
        return toAjax(dictTypeService.updateDictType(update(dict, ShiroUtils.getLoginName())));
    }

    /**
     * 查询字典详细
     */
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap modelMap) {
        modelMap.put("dict", dictTypeService.getById(dictId));
        modelMap.put("dictList", dictTypeService.list());
        return "system/dict/data/data";
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(dictTypeService.deleteDictTypeByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验字典类型
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public String checkDictTypeUnique(SysDictType dictType) {
        return dictTypeService.checkDictTypeUnique(dictType);
    }

}