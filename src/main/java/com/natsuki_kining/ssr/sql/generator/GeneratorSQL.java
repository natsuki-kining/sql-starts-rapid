package com.natsuki_kining.ssr.sql.generator;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.QueryRule;
import com.natsuki_kining.ssr.utils.Constant;
import org.springframework.stereotype.Component;

/**
 * 生成sql
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:19
 */
@Component
public class GeneratorSQL implements Generator {

    @Override
    public String generateQuerySQL(QueryRule queryRule, QueryParams queryParams) {
        return null;
    }

    @Override
    public void generateSortSQL(StringBuilder stringBuilder, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getSort() == null || queryParams.getSorts() == null){
            return;
        }
    }

    @Override
    public void generatePageSQL(StringBuilder stringBuilder, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getPageSize() == Constant.QueryPage.QUERY_ALL){
            return;
        }
    }
}
