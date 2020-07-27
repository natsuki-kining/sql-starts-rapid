-- Create table
create table SSR_DYNAMIC_SQL
(
  id            VARCHAR2(32) not null,
  query_code    VARCHAR2(100) not null,
  query_name    VARCHAR2(100),
  query_type    VARCHAR2(50),
  sql_template  CLOB not null,
  before_script CLOB,
  after_script  CLOB,
  version       VARCHAR2(20),
  del_flag      NUMBER(1),
  order_num     NUMBER(11),
  create_name   VARCHAR2(60),
  create_id     VARCHAR2(64),
  create_time   TIMESTAMP(6),
  update_name   VARCHAR2(60),
  update_id     VARCHAR2(64),
  update_time   TIMESTAMP(6),
  remark        VARCHAR2(1000)
);
-- Add comments to the table 
comment on table SSR_DYNAMIC_SQL
  is '动态SQL';
-- Add comments to the columns 
comment on column SSR_DYNAMIC_SQL.id
  is '主键';
comment on column SSR_DYNAMIC_SQL.query_code
  is '查询的code';
comment on column SSR_DYNAMIC_SQL.query_name
  is '查询的名称';
comment on column SSR_DYNAMIC_SQL.query_type
  is '查询的类型';
comment on column SSR_DYNAMIC_SQL.sql_template
  is 'sql模板';
comment on column SSR_DYNAMIC_SQL.before_script
  is '查询之前的处理脚本。';
comment on column SSR_DYNAMIC_SQL.after_script
  is '查询之后的处理脚本。';
comment on column SSR_DYNAMIC_SQL.version
  is '版本号';
comment on column SSR_DYNAMIC_SQL.del_flag
  is '是否删除。1：是，0：否。';
comment on column SSR_DYNAMIC_SQL.order_num
  is '排序编号。正序。';
comment on column SSR_DYNAMIC_SQL.create_name
  is '创建人名称';
comment on column SSR_DYNAMIC_SQL.create_id
  is '创建人id';
comment on column SSR_DYNAMIC_SQL.create_time
  is '创建时间';
comment on column SSR_DYNAMIC_SQL.update_name
  is '修改人名称';
comment on column SSR_DYNAMIC_SQL.update_id
  is '修改人id';
comment on column SSR_DYNAMIC_SQL.update_time
  is '修改时间';
comment on column SSR_DYNAMIC_SQL.remark
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SSR_DYNAMIC_SQL
  add constraint SSR_PRIMARY primary key (ID);
alter table SSR_DYNAMIC_SQL
  add constraint SSR_UNIQUE unique (QUERY_CODE);