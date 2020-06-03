package com.natsuki_kining.ssr.core.rule;

import com.natsuki_kining.ssr.core.beans.QueryRule;

/**
 * rule接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 17:32
 */
public interface Rule {

    /**
     * 解析code生成查询规则
     *
     * @param queryCode
     * @return
     */
    QueryRule analysis(String queryCode);
}
