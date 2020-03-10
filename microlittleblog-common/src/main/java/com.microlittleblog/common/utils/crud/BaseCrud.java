package com.microlittleblog.common.utils.crud;

import com.microlittleblog.common.core.domain.BaseEntity;

/**
 * @author jihongyuan
 * @date 2020/1/14 15:42
 */
public interface BaseCrud<T> {

    /**
     * before
     *
     * @param t t
     */
    default void before(T t){}

    /**
     * after
     *
     * @param t t
     */
     default void after(T t){}

}