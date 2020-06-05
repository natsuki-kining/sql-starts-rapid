package com.natsuki_kining.ssr.core.utils;

import com.natsuki_kining.ssr.core.enums.QueryConnect;
import com.natsuki_kining.ssr.core.enums.QueryOperationalCharacter;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/6/5 18:32
 **/
public class BeanUtils {

    public static QueryConnect getQueryConnect(String key){
        QueryConnect queryConnect = Constant.QUERY_CONNECT_MAP.get(key);
        if (queryConnect == null){
            queryConnect = QueryConnect.AND;
        }
        return queryConnect;
    }

    public static QueryOperationalCharacter getQueryOperationalCharacter(String key){
        QueryOperationalCharacter queryOperationalCharacter = Constant.QUERY_OPERATIONAL_CHARACTER_MAP.get(key);
        if (queryOperationalCharacter == null){
            queryOperationalCharacter = QueryOperationalCharacter.EQ;
        }
        return queryOperationalCharacter;
    }
}
