package com.natsuki_kining.ssr.core.config.multisource;

import com.alibaba.druid.pool.DruidDataSource;
import com.natsuki_kining.ssr.core.config.properties.SSRDruidProperties;
import com.natsuki_kining.ssr.core.config.properties.SSRProperties;
import com.natsuki_kining.ssr.core.utils.Constant;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/4 22:25 multisource
 */
@Component
public class MultiSourceConfig {

    @Autowired
    private SSRProperties ssrProperties;

    @Autowired
    private SSRDruidProperties SSRDruidProperties;

    private Map<String,String> dbTypeMap = new HashMap<>();

    /**
     * 主数据源
     */
    private DruidDataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        SSRDruidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    private DynamicDataSource multiDataSource() {
        DruidDataSource masterDataSource = masterDataSource();
        Map<Object, Object> multiDruidDataSourceMap = new HashMap<>();
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
     * @return
     */
    public String getCurrentThreadDbType(){
        String dataSourceName = DataSourceContextHolder.getDataSourceName();
        if (StringUtils.isBlank(dataSourceName)){
            dataSourceName = Constant.MultiDataSource.masterDataSourceName;
        }
        return dbTypeMap.get(dataSourceName);
    }


}
