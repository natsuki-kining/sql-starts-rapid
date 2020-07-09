package com.natsuki_kining.ssr.core.utils;

import java.util.Collection;

/**
 * 断言工具类
 *
 * @author natsuki_kining
 */
public class Assert {

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean flag, String message) {
        if (flag) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void collectionIsEmpty(Collection collection, String message) {
        if (collection == null || collection.size() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFalse(boolean flag, String message) {
        if (!flag) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNotBlank(String str, String message) {
        if (StringUtils.isNotBlank(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException(message);
        }
    }

}
