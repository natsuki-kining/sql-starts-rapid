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

    public static final Map<String,String> COMMON_CONDITION = new HashMap<String, String>(){{
        put("and",SQLCondition.Common.AND);
        put("or",SQLCondition.Common.OR);
        put("eq",SQLCondition.Common.EQ);
        put("lt",SQLCondition.Common.LT);
        put("le",SQLCondition.Common.LE);
        put("gt",SQLCondition.Common.GT);
        put("ge",SQLCondition.Common.GE);
    }};

    public static final Map<String,String> MYSQL_CONDITION = new HashMap<String, String>(){{
        put("ll",SQLCondition.MySql.L_LIKE);
        put("rl",SQLCondition.MySql.R_LIKE);
        put("al",SQLCondition.MySql.A_LIKE);
    }};

    public static final Map<String,String> ORACLE_CONDITION = new HashMap<String, String>(){{
        put("ll",SQLCondition.Oracle.L_LIKE);
        put("rl",SQLCondition.Oracle.R_LIKE);
        put("al",SQLCondition.Oracle.A_LIKE);
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

    public interface SQLCondition{

        String paramName = ":name";

        interface Common{
            String AND = "AND ";
            String OR = "OR ";

            String EQ = "= ";
            String LT = "< ";
            String LE = "<= ";
            String GT = "> ";
            String GE = ">= ";
        }

        interface Oracle{
            String L_LIKE = "LIKE '%' || :name ";
            String R_LIKE = "LIKE :name || '%' ";
            String A_LIKE = "LIKE '%' || :name || '%' ";
        }

        interface MySql{
            String L_LIKE = "LIKE concat('%',:name) ";
            String R_LIKE = "LIKE concat(:name,'%') ";
            String A_LIKE = "LIKE concat(concat('%',:name),'%') ";
        }
    }
}
