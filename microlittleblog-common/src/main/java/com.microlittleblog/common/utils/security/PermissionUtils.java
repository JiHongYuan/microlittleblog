package com.microlittleblog.common.utils.security;

import com.microlittleblog.common.constant.MessageConstants;
import com.microlittleblog.common.constant.PermissionConstants;
import com.microlittleblog.common.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * permission 工具类
 *
 * @author ruoyi
 */
public class PermissionUtils {
    private static final Logger log = LoggerFactory.getLogger(PermissionUtils.class);

    /**
     * 权限错误消息提醒
     *
     * @param permissionsStr 错误信息
     * @return 提示信息
     */
    public static String getMsg(String permissionsStr) {
        String permission = StringUtils.substringBetween(permissionsStr, "[", "]");
        String msg = MessageUtils.message(MessageConstants.NO_PERMISSION, permission);
        if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.ADD_PERMISSION)) {
            msg = MessageUtils.message(MessageConstants.CREATE_PERMISSION, permission);

        } else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.EDIT_PERMISSION)) {
            msg = MessageUtils.message(MessageConstants.UPDATE_PERMISSION, permission);

        } else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.REMOVE_PERMISSION)) {
            msg = MessageUtils.message(MessageConstants.DELETE_PERMISSION, permission);

        } else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.EXPORT_PERMISSION)) {
            msg = MessageUtils.message(MessageConstants.EXPORT_PERMISSION, permission);

        } else if (StringUtils.endsWithAny(permission, new String[]{PermissionConstants.VIEW_PERMISSION, PermissionConstants.LIST_PERMISSION})) {
            msg = MessageUtils.message(MessageConstants.VIEW_PERMISSION, permission);
        }
        return msg;
    }

}