package com.natsuki_kining.ssr.core.config.multisource;

import com.alibaba.druid.pool.DruidDataSource;
import com.natsuki_kining.ssr.core.config.properties.SSRDruidProperties;
import com.natsuki_kining.ssr.core.config.properties.SSRProperties;
import com.natsuki_kining.ssr.core.utils.Constant;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 多数据源配置
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/4 22:25 multisource
 */
@Slf4j
@Component
public class MultiSourceConfig {

    @Autowired
    private SSRProperties ssrProperties;

    @Autowired
    private SSRDruidProperties SSRDruidProperties;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 数据源名称，数据源类型
     */
    private Map<String,String> dbTypeMap = new HashMap<>();

    /**
     * 数据源名称，数据源
     */
    private Map<Object, Object> multiDruidDataSourceMap = new HashMap<>();

    /**
     * 主数据源
     * @return DruidDataSource
     */
    private DruidDataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        SSRDruidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 多数据源连接池配置
     * @return DynamicDataSource
     */
    @Bean
    private DynamicDataSource multiDataSource() {
        DruidDataSource masterDataSource = masterDataSource();
        multiDruidDataSourceMap.put(Constant.MultiDataSource.masterDataSourceName, masterDataSource);
        if (ssrProperties.getMultiDataSource() != null && ssrProperties.getMultiDataSource().size() > 0) {
            ssrProperties.getMultiDataSource().forEach((k, v) -> {
                DruidDataSource druidDataSource = new DruidDataSource();
                v.config(druidDataSource);
                multiDruidDataSourceMap.put(k, druidDataSource);
            });
        }

        multiDruidDataSourceMap.entrySet().forEach(v -> {
            try {
                ((DruidDataSource) v.getValue()).init();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        multiDruidDataSourceMap.forEach((k,v)->{
            DruidDataSource value = ((DruidDataSource) v);
            dbTypeMap.put((String)k,value.getDbType());
        });

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        dynamicDataSource.setTargetDataSources(multiDruidDataSourceMap);
        return dynamicDataSource;
    }

    /**
     * 获取当前执行的数据类型
     * @return 当前线程操作数据源的类型
     */
    public String getCurrentThreadDbType(){
        String dataSourceName = DataSourceContextHolder.getDataSourceName();
        if (StringUtils.isBlank(dataSourceName)){
            dataSourceName = Constant.MultiDataSource.masterDataSourceName;
        }
        return dbTypeMap.get(dataSourceName);
    }

    /**
     * 是否已包含dataSourceName
     * @param dataSourceName 数据源名称
     * @return 包含返回true，否则false
     */
    public boolean containsDataSourceName(String dataSourceName){
        return multiDruidDataSourceMap.containsKey(dataSourceName);
    }

    /**
     * 动态新增数据源
     * @param druidProperties 数据源配置
     * @return 是否成功
     */
    public boolean addDataSource(SSRDruidProperties druidProperties) {
        if (druidProperties == null || !druidProperties.check()) {
            return false;
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidProperties.config(druidDataSource);
        try {
            druidDataSource.init();
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            return false;
        }
        String dataSourceName = druidProperties.getDataSourceName();
        multiDruidDataSourceMap.put(dataSourceName,druidDataSource);
        dbTypeMap.put(dataSourceName, druidDataSource.getDbType());
        DynamicDataSource dynamicDatasource = applicationContext.getBean(DynamicDataSource.class);
        dynamicDatasource.setTargetDataSources(multiDruidDataSourceMap);
        dynamicDatasource.afterPropertiesSet();
        return true;
    }

    /**
     * 动态移除数据源
     * @param dataSourceName 数据源名称
     * @return 是否成功
     */
    public boolean removeDataSource(String dataSourceName) {
        multiDruidDataSourceMap.remove(dataSourceName);
        dbTypeMap.remove(dataSourceName);
        DynamicDataSource dynamicDatasource = applicationContext.getBean(DynamicDataSource.class);
        dynamicDatasource.setTargetDataSources(multiDruidDataSourceMap);
        dynamicDatasource.afterPropertiesSet();
        return true;
    }

    /**
     * 获取所有的数据源名称
     * @return 所有的数据源名称
     */
    public Set<Object> getDataSourceName(){
        return multiDruidDataSourceMap.keySet();
    }
}
