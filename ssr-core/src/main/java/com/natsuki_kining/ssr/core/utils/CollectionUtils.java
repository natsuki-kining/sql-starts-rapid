package com.natsuki_kining.ssr.core.utils;

import java.util.Collection;

/**
 * Collection Utils
 *
 * @Author natsuki_kining
 * @Date 2020/6/6 20:34
 **/
public class CollectionUtils {

    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }
}
