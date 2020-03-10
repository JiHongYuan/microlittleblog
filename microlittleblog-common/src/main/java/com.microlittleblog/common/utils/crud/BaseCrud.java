package com.microlittleblog.common.utils.crud;

import com.microlittleblog.common.core.domain.BaseEntity;

/**
 * @author jihongyuan
 * @date 2020/1/14 15:42
 */
public interface BaseCrud<T extends BaseEntity> {

    /**
     * before
     *
     * @param t t
     */
    default void insertBefore(T t) {
    }

    /**
     * before
     *
     * @param t t
     */
    default void updateBefore(T t) {
    }
}