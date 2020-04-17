package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/17 18:00
 */
@ConditionalOnProperty(prefix = "ssr", name = "orm.type", havingValue = "mybatis")
@Component
public class MybatisDao implements Dao {
    public Object query(QueryParams queryParams, String sql) {
        return null;
    }
}
