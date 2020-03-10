package com.microlittleblog.common.utils.crud;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.common.core.domain.BaseEntity;
import com.microlittleblog.common.utils.reflect.ReflectUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jihongyuan
 * @date 2020/1/10 14:51
 */
public class CrudUtil {

    /**
     * update 解决一对多问题
     */
    public static <T> void updateCollectionHelper(Wrapper<T> wrapper, List<T> newList, IService<T> service, Class<T> clazz, BaseCrud<T> baseCrud) {
        String tableIdName = getTableIdName(clazz);
        Assert.notNull(tableIdName, "tableId Name is null");

        List<Long> oldIdList = new ArrayList<>();
        List<T> oldList = service.list(wrapper);
        for (Object obj : oldList) {
            oldIdList.add(ReflectUtils.invokeGetter(obj, tableIdName));
        }

        for (T obj : newList) {
            Long id = ReflectUtils.invokeGetter(obj, tableIdName);
            baseCrud.before(obj);
            if (oldIdList.contains(id)) {
                service.updateById(obj);
                oldIdList.remove(id);
            } else if (id == null) {
                service.save(obj);
            }
            baseCrud.after(obj);
        }
        service.removeByIds(oldIdList);
    }

    private static <T> String getTableIdName(Class<T> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(TableId.class)) {
                return field.getName();
            }
        }
        return null;
    }

}