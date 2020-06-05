package com.natsuki_kining.ssr.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 0:12
 */
public class Constant {

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

    public interface Condition{
        String LEFT_LIKE = "ll";
        String RIGHT_LIKE = "rl";
        String ALL_LIKE = "al";
        String EQ = "=";

        String AND = "AND";

        Map<String,String> QUERY_CONNECT_MAP = new HashMap<String, String>(){{
            put("and", "AND");
            put("or","OR");
        }};

        Map<String,String> QUERY_OPERATIONAL_CHARACTER_MAP = new HashMap<String, String>(){{
            put(EQ, EQ);
            put("neq","<>");
            put("lt","<");
            put("le","<=");
            put("gt",">");
            put("ge",">=");
            put(LEFT_LIKE, LEFT_LIKE);
            put(RIGHT_LIKE, RIGHT_LIKE);
            put(ALL_LIKE, ALL_LIKE);
        }};
    }
}
