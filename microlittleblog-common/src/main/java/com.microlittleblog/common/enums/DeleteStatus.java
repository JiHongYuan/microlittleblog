package com.microlittleblog.common.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum DeleteStatus {

    /**
     * 正常
     */
    OK("0", "正常"),

    /**
     * 删除
     */
    DELETED("1", "删除"),

    /**
     * 停用
     */
    DISABLE("-1", "停用"),
    ;

    private final String code;
    private final String info;

    DeleteStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}