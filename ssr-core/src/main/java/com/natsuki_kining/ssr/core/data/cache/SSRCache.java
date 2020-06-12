package com.natsuki_kining.ssr.core.data.cache;

import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.SSRData;

/**
 * 缓存接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/27 20:50
 */
public interface SSRCache extends SSRData {

    /**
     * 缓存 SSRDynamicSQL
     * @param dynamicSQL SSRDynamicSQL
     * @return 是否成功
     */
    boolean save(SSRDynamicSQL dynamicSQL);

    /**
     * 删除缓存
     * @param queryCode 查询code
     * @return 是否成功
     */
    boolean delete(String queryCode);



}
