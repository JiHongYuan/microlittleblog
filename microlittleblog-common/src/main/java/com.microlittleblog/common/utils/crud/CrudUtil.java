package com.microlittleblog.common.utils.crud;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.microlittleblog.common.core.domain.BaseEntity;
import com.microlittleblog.common.utils.reflect.ReflectUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jihongyuan
 * @date 2020/1/10 14:51
 */
public class CrudUtil {

    /**
     * update 解决一对多问题
     * 多主键 不适合
     */
    public static <T extends BaseEntity> void updateCollectionHelper(Wrapper<T> wrapper, List<T> newList, IService<T> service, Class<T> clazz, BaseCrud<T> baseCrud) {
        String tableIdName = getTableIdName(clazz);
        Assert.notNull(tableIdName, "table id name is null");

        List<Long> oldIdList = new ArrayList<>();
        List<T> oldList = service.list(wrapper);
        for (Object obj : oldList) {
            oldIdList.add(ReflectUtils.invokeGetter(obj, tableIdName));
        }

        for (T obj : newList) {
            Long id = ReflectUtils.invokeGetter(obj, tableIdName);
            if (oldIdList.contains(id)) {
                // 只有当新旧数据不相等 才执行update
                if (!oldList.contains(obj)) {
                    baseCrud.updateBefore(obj);
                    obj.setUpdateTime(new Date());
                    service.updateById(obj);
                }
                oldIdList.remove(id);
            } else if (id == null) {
                baseCrud.insertBefore(obj);
                obj.setCreateTime(new Date());
                service.save(obj);
            }
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