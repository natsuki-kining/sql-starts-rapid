package com.natsuki_kining.ssr.core.utils;

import java.util.Map;

/**
 * map util
 *
 * @Author natsuki_kining
 * @Date 2020/6/6 20:34
 **/
public class MapUtils {

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}
