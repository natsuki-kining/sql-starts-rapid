package com.natsuki_kining.ssr.core.utils;

import com.natsuki_kining.ssr.core.enums.QueryConnect;
import com.natsuki_kining.ssr.core.enums.QueryOperationalCharacter;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 0:12
 */
public class Constant {

    public static final String QUERY_PARAMS_NAME = ":name";

    public static final Map<String,QueryConnect> QUERY_CONNECT_MAP = new HashMap<String, QueryConnect>(){{
        put("and", QueryConnect.AND);
        put("or",QueryConnect.OR);
    }};

    public static final Map<String,QueryOperationalCharacter> QUERY_OPERATIONAL_CHARACTER_MAP = new HashMap<String, QueryOperationalCharacter>(){{
        put("eq",QueryOperationalCharacter.EQ);
        put("neq",QueryOperationalCharacter.NEQ);
        put("lt",QueryOperationalCharacter.LT);
        put("le",QueryOperationalCharacter.LE);
        put("gt",QueryOperationalCharacter.GT);
        put("ge",QueryOperationalCharacter.GE);
        put("ll",QueryOperationalCharacter.L_LIKE);
        put("rl",QueryOperationalCharacter.R_LIKE);
        put("al",QueryOperationalCharacter.A_LIKE);
    }};

    /**
     * 查询类型常量
     */
    public interface QueryCodeType {
        String GENERATE_BY_ENTITY = "generateByEntity";
        String GENERATE_BY_TABLE = "generateByTable";
        String SINGLE = "single";
    }

    /**
     * 分页常量
     */
    public interface QueryPage{
        int QUERY_ALL = -1;
        //默认一页10条数据
        int DEFAULT_PAGE_SIZE = 10;
    }

}
