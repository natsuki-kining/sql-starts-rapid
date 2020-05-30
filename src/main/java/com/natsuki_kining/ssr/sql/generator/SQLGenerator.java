package com.natsuki_kining.ssr.sql.generator;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.sql.SQL;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:19
 */
public interface SQLGenerator {

    String generate(QueryParams queryParams);

}
