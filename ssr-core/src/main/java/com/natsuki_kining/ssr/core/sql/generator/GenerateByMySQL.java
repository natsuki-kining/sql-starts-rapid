package com.natsuki_kining.ssr.core.sql.generator;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.utils.Constant;
import com.natsuki_kining.ssr.core.utils.StringUtils;

import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 21:36
 */
public class GenerateByMySQL extends AbstractGeneratorSQL implements Generator {

    @Override
    protected String getLikeConditionSign(QueryParams queryParams, String k) {
        return Constant.MYSQL_CONDITION.get(k);
    }
}
