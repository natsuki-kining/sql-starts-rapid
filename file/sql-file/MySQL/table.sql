CREATE DATABASE /*!32312 IF NOT EXISTS */`ssr` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ssr`;

DROP TABLE IF EXISTS `ssr_dynamic_sql`;

CREATE TABLE `ssr_dynamic_sql`
(
    `ID`            varchar(32)  NOT NULL COMMENT '主键',
    `QUERY_CODE`    varchar(100) NOT NULL COMMENT '查询的code',
    `QUERY_NAME`    varchar(100)  DEFAULT NULL COMMENT '查询的名称',
    `QUERY_TYPE`    varchar(50)   DEFAULT NULL COMMENT '查询的类型',
    `DATA_SOURCE_NAME` varchar(100) DEFAULT NULL COMMENT '数据源名称，空则使用默认数据源',
    `SQL_TEMPLATE`  text         NOT NULL COMMENT 'sql模板',
    `BEFORE_SCRIPT` text COMMENT '查询之前脚本',
    `AFTER_SCRIPT`  text COMMENT '查询之后脚本',
    `VERSION`       varchar(20)   DEFAULT NULL COMMENT '版本号',
    `DEL_FLAG`      bit(1)        DEFAULT NULL COMMENT '是否删除。1：是，0：否。',
    `ORDER_NUM`     int(11)       DEFAULT NULL COMMENT '排序编号。正序。',
    `CREATE_NAME`   varchar(60)   DEFAULT NULL COMMENT '创建人名称',
    `CREATE_Id`     varchar(64)   DEFAULT NULL COMMENT '创建人id',
    `CREATE_TIME`   datetime      DEFAULT NULL COMMENT '创建时间',
    `UPDATE_NAME`   varchar(60)   DEFAULT NULL COMMENT '修改人名称',
    `UPDATE_Id`     varchar(64)   DEFAULT NULL COMMENT '修改人id',
    `UPDATE_TIME`   datetime      DEFAULT NULL COMMENT '修改时间',
    `REMARK`        varchar(1000) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UNIQUE` (`QUERY_CODE`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
