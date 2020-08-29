package com.natsuki_kining.ssr.hibernate.sql.generator;

import com.natsuki_kining.ssr.core.sql.generator.AbstractGeneratorByMySQL;
import com.natsuki_kining.ssr.core.sql.generator.Generator;
import com.natsuki_kining.ssr.core.utils.Constant;

/**
 * mysql生成sql类
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 21:36
 */
public class GenerateByMySQL extends AbstractGeneratorByMySQL implements Generator {

    @Override
    protected String likeConditionHandel(String replacement, String placeholderParam) {
        if (Constant.Condition.ALL_LIKE.equals(replacement)) {
            return "LIKE concat(concat('%'," + placeholderParam + "),'%') ";
        } else if (Constant.Condition.LEFT_LIKE.equals(replacement)) {
            return "LIKE concat('%'," + placeholderParam + ") ";
        } else {
            return "LIKE concat(" + placeholderParam + ",'%') ";
        }
    }

    @Override
    protected String placeholderParam(String queryCode) {
        return ":" + queryCode + " ";
    }
}
