package com.natsuki_kining.ssr.core.config.multisource;

import com.natsuki_kining.ssr.core.utils.Constant;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * @author xiongneng
 * @since 2017年3月5日 上午9:11:49
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DataSourceContextHolder.getDataSourceName();
        if (StringUtils.isBlank(dataSourceName)){
            dataSourceName = Constant.MultiDataSource.masterDataSourceName;
        }
        return dataSourceName;
    }

}
