package com.natsuki_kining.ssr.core.config.multisource;

import com.natsuki_kining.ssr.core.sql.generator.Generator;

/**
 * datasource的上下文
 *
 * @author xiongneng
 * @since 2017年3月5日 上午9:10:58
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> dataSource = new ThreadLocal<>();

    private static final ThreadLocal<Generator> generatorType = new ThreadLocal<>();

    /**
     * @param dataSourceType 数据库类型
     * @Description: 设置数据源类型
     */
    public static void setDataSourceName(String dataSourceType) {
        dataSource.set(dataSourceType);
    }

    /**
     * @Description: 获取数据源类型
     */
    public static String getDataSourceName() {
        return dataSource.get();
    }

    /**
     * @Description: 清除数据源类型，以及sql生成类型
     */
    public static void clearDataSourceName() {
        dataSource.remove();
        generatorType.remove();
    }
    /**
     * @param generator sql生成类型
     * @Description: 设置sql生成类型
     */
    public static void setGeneratorType(Generator generator) {
        generatorType.set(generator);
    }

    /**
     * @Description: 获取sql生成类型
     */
    public static Generator getGeneratorType() {
        return generatorType.get();
    }
}
