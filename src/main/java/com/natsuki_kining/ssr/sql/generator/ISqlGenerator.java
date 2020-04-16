package com.natsuki_kining.ssr.sql.generator;

import com.natsuki_kining.ssr.beans.QueryParams;

/**
 *
 *
 * @Author natsuki_kining
 * @Date 2020-4-12 23:03:53
 * @Version 1.0.0
 **/
public interface ISqlGenerator {

    String getSql(QueryParams queryParams);
}
