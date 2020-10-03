package com.natsuki_kining.ssr.core.config.multisource;

import com.natsuki_kining.ssr.core.utils.Constant;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * @author xiongneng
 * @since 2017年3月5日 上午9:11:49
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Autowired
    private MultiSourceConfig multiSourceConfig;

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DataSourceContextHolder.getDataSourceName();
        if (StringUtils.isNotBlank(dataSourceName) && !multiSourceConfig.multiDruidDataSourceMap.containsKey(dataSourceName)){
            log.warn("没有找到数据源：{}，将使用默认数据源：{}。",dataSourceName,Constant.MultiDataSource.masterDataSourceName);
        }
        return dataSourceName;
    }

}
