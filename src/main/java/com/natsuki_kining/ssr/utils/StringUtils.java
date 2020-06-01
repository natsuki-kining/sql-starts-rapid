package com.natsuki_kining.ssr.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串工具类
 *
 * @author natsuki_kining
 */
public class StringUtils {

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 将首字母变成大写
     *
     * @param str 需要改变的字符串
     * @return 首字母变大写后的字符串
     */
    public static String capitalize(final String str) {
        if (isBlank(str)) {
            return str;
        }
        final char firstChar = str.charAt(0);
        if (Character.isTitleCase(firstChar)) {
            return str;
        }
        return Character.toTitleCase(firstChar) + str.substring(1);
    }

    /**
     * 将类属性名转换成数据库字段名
     * code->CODE
     * userName->USER_NAME
     * @param fieldName
     * @return
     */
    public static String castFieldToColumn(String fieldName){
        char[] chars = fieldName.toCharArray();
        StringBuilder column = new StringBuilder(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                column.append("_");
            }
            column.append(chars[i]);
        }
        return column.toString().toUpperCase();
    }


}
