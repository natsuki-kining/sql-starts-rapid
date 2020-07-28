-- Create table
create table SSR_DYNAMIC_SQL
(
  ID            VARCHAR2(32) not null,
  QUERY_CODE    VARCHAR2(100) not null,
  QUERY_NAME    VARCHAR2(100),
  QUERY_TYPE    VARCHAR2(50),
  SQL_TEMPLATE  CLOB not null,
  BEFORE_SCRIPT CLOB,
  AFTER_SCRIPT  CLOB,
  VERSION       VARCHAR2(20),
  DEL_FLAG      NUMBER(1),
  ORDER_NUM     NUMBER(11),
  CREATE_NAME   VARCHAR2(60),
  CREATE_ID     VARCHAR2(64),
  CREATE_TIME   TIMESTAMP(6),
  UPDATE_NAME   VARCHAR2(60),
  UPDATE_ID     VARCHAR2(64),
  UPDATE_TIME   TIMESTAMP(6),
  REMARK        VARCHAR2(1000)
);
-- Add comments to the table 
comment on table SSR_DYNAMIC_SQL
  is '动态SQL';
-- Add comments to the columns 
comment on column SSR_DYNAMIC_SQL.ID
  is '主键';
comment on column SSR_DYNAMIC_SQL.QUERY_CODE
  is '查询的code';
comment on column SSR_DYNAMIC_SQL.QUERY_NAME
  is '查询的名称';
comment on column SSR_DYNAMIC_SQL.QUERY_TYPE
  is '查询的类型';
comment on column SSR_DYNAMIC_SQL.SQL_TEMPLATE
  is 'sql模板';
comment on column SSR_DYNAMIC_SQL.BEFORE_SCRIPT
  is '查询之前的处理脚本。';
comment on column SSR_DYNAMIC_SQL.AFTER_SCRIPT
  is '查询之后的处理脚本。';
comment on column SSR_DYNAMIC_SQL.VERSION
  is '版本号';
comment on column SSR_DYNAMIC_SQL.DEL_FLAG
  is '是否删除。1：是，0：否。';
comment on column SSR_DYNAMIC_SQL.ORDER_NUM
  is '排序编号。正序。';
comment on column SSR_DYNAMIC_SQL.CREATE_NAME
  is '创建人名称';
comment on column SSR_DYNAMIC_SQL.CREATE_ID
  is '创建人id';
comment on column SSR_DYNAMIC_SQL.CREATE_TIME
  is '创建时间';
comment on column SSR_DYNAMIC_SQL.UPDATE_NAME
  is '修改人名称';
comment on column SSR_DYNAMIC_SQL.UPDATE_ID
  is '修改人id';
comment on column SSR_DYNAMIC_SQL.UPDATE_TIME
  is '修改时间';
comment on column SSR_DYNAMIC_SQL.REMARK
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SSR_DYNAMIC_SQL
  add constraint SSR_PRIMARY primary key (ID);
alter table SSR_DYNAMIC_SQL
  add constraint SSR_UNIQUE unique (QUERY_CODE);