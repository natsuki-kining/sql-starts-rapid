package com.natsuki_kining.ssr.core.data.cache;

import com.natsuki_kining.ssr.core.data.SSRData;

/**
 * 缓存接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/27 20:50
 */
public interface SSRCache extends SSRData {

    /**
     * 根据code获取缓存中的数据
     *
     * @param code  缓存的key
     * @param clazz 泛型类
     * @param <T>   泛型
     * @return 缓存的值
     */
    <T> T get(String code, Class<T> clazz);

    /**
     * 缓存数据
     *
     * @param code   缓存的key
     * @param object 缓存的值
     * @return 是否成功
     */
    boolean save(String code, Object object);

    /**
     * 删除缓存
     *
     * @param code 缓存的key
     * @return 是否成功
     */
    boolean delete(String code);

    /**
     * 清楚全部缓存
     * @return
     */
    boolean clean();
}
