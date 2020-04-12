package com.natsuki_kining.ssr.beans;

import lombok.Data;

/**
 *
 *
 * @Author natsuki_kining
 * @Date 2020-4-12 23:03:53
 * @Version 1.0.0
 **/
@Data
public class SSRDynamicSqlVO {

	/**
	 * 主键
	 */
	private String id;
    
	/**
	 * 查询的code
	 */
	private String queryCode;
    
	/**
	 * 查询的名称
	 */
	private String queryName;
    
	/**
	 * 执行sql 的freemarker
	 */
	private String sqlFreemarker;
    
	/**
	 * 版本号
	 */
	private Integer version;

}