# sql-starts-rapid

-----
#目录

----
# 一 介绍
## 1.1 简称：ssr（可以期待下个项目->[ur](https://gitee.com/natsuki_kining/ultra-rare )）

## 1.2 主要功能
* 可根据传入的参数动态生成查询sql
* 自带拦截器可修改传入的参数跟带入固定的查询条件（例如登录用户的id）
* 内部集成三种脚本语言可以预处理查询结果
* 集成hibernate跟mybatis两种orm查询框架
* 实现多数据源动态配置

## 1.3 使用场景 
* 报表查询
* 不需要编写java代码便可构建一个SQL查询
* 经常需要修改SQL语句的查询
* 可以在运行时修改sql，而不用重启项目。
* 可以快速的新增一个查询功能，而不用写任何代码。
* 不用再每个查询都暴露一个api方法

## 1.4 各版本主功能说明
* 第1.0版：
    * 通过freemarker实现动态sql。
    * Java拦截器，用于处理查询前后的数据。
    * 脚本拦截器，可调用JavaScript，Python，Groovy等脚本语言处理数据。
    * 缓存，sql查询效率，提供sql优化建议等工具。
    * 多code查询，用于处理内部有多查询的情况。
    * 多数据源，可以设置动态多数据源。
* 第2.0版：
    * sql查询计划解析，分析sql查询计划，记录慢sql，提出修改建议。

# 二 使用说明

## 2.1 快速体验（运行的是test-ssr-mybatis项目）

* ssr_user中的数据     
![query-user](file/img/2.1-0.png)

### 2.1.1 简单sql查询

* 在ssr_dynamic_sql 表中新增数据：
```sql
INSERT INTO `ssr_dynamic_sql` (`ID`, `QUERY_CODE`, `SQL_TEMPLATE`) VALUES ('1', 'query-user', 'select * from ssr_user');
``` 

* postman请求   
![query-user-result](file/img/2.1.1-2.png)

### 2.1.2 带查询条件查询
* 使用freemarker为sql添加查询条件
```sql
INSERT INTO `ssr_dynamic_sql` (`ID`, `QUERY_CODE`, `SQL_TEMPLATE`) VALUES ('1', 'query-user', 'select * from ssr_user \r\nwhere 1=1\r\n<#if name??><#noparse>\r\nand NAME like CONCAT(\'%\',#{name},\'%\')\r\n</#noparse></#if>\r\n<#if userName??><#noparse>\r\nand USER_NAME like CONCAT(\'%\',#{userName},\'%\')\r\n</#noparse></#if>\r\n<#if code??><#noparse>\r\nand `CODE` = #{code}\r\n</#noparse></#if>');
```
![query-user](file/img/2.1.2-1.png)
> 其中`<#if>`为添加判断，`<#noparse>`为freemarker`#{}`转义
* postman请求      
![query-user](file/img/2.1.2-2.png)
> 输出的查询sql语句为 select * from ssr_user  where 1=1 and NAME like CONCAT('%',?,'%')
* postman请求      
![query-user](file/img/2.1.2-3.png)
> 输出的查询sql语句为 select * from ssr_user  where 1=1 and NAME like CONCAT('%',?,'%') and USER_NAME like CONCAT('%',?,'%')

### 2.1.3 返回封装的类型
* 在query接口里传入需要转换的类型
```java
    @PostMapping("queryUser")
    public Object queryUser(@RequestBody QueryParams queryParams) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.natsuki_kining.ssr.test.entity.SSRUser");
        List<?> result = this.query.queryList(queryParams, aClass);
        return result;
    }
```
* postman请求  
![query-user](file/img/2.1.3-1.png)

## 2.2 快速入门

### 2.2.1 新增表 SSR_DYNAMIC_SQL
    脚本在file文件夹里，根据使用的数据库选择对应的脚本

### 2.2.2 引入依赖
根据需求引入相应的依赖包
默认实现了mybatis跟hibernate，如果项目是使用其他的orm框架的，可以引入ssr-core

    <dependency>
         <groupId>com.natsuki_kining.ssr</groupId>
         <artifactId>ssr-core</artifactId>
         <version>1.0.0</version>
    </dependency>
然后实现QueryORM接口，如果是使用mybatis的可以直接引入

    <dependency>
         <groupId>com.natsuki_kining.ssr</groupId>
         <artifactId>ssr-mybatis</artifactId>
         <version>1.0.0</version>
    </dependency>
    
如果是使用hibernate的则引入

        <dependency>
             <groupId>com.natsuki_kining.ssr</groupId>
             <artifactId>ssr-hibernate</artifactId>
             <version>1.0.0</version>
        </dependency>

### 2.2.3 添加包扫描路径
    com.natsuki_kining
    
### 2.2.4 在controller里注入Query并调用里面的方法
```java
@RestController
@RequestMapping("query")
public class QueryController {

    @Autowired
    private Query query;
    
    @PostMapping("page")
    public Object page(@RequestBody QueryParams queryParams) {
        return this.query.query(queryParams);
    }

}
```
## 2.3 进阶

### 2.3.1 Query接口介绍

### 2.3.1 返回指定的类型
Query接口里的每个方法都有个重载方法，可传输指定的类型，直接调用为Map类型。


### 2.3.1 分页跟排序
* 分页
设置pageNo跟pageSize两个参数即可。    
![query-user-result](file/img/2.3.1-1.png)  
输出的sql语句：
> select * from ssr_user LIMIT 0,2

* 排序
设置sort参数即可    
![query-user-result](file/img/2.3.1-2.png)
输出的sql语句：
> select * from ssr_user ORDER BY NAME DESC

### 2.3.1 Java拦截器使用
* 直接继承AbstractQueryJavaIntercept类，然后重写里面方法，交给spring管理。如果没有`@QueryCode`加入这个注解，则默认拦截所有的查询。`@QueryCode`注解支持正则表达式，只拦截匹配的queryCode
例： 
```java
@Component
public class SSRQueryIntercept extends AbstractQueryJavaIntercept {

    @Override
    public boolean preHandle(QueryParams queryParams) {
        return true;
    }
    @Override
    public void queryBefore(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL ssrDynamicSQL, Map<String, Object> map) {
        System.out.println("master intercept:"+queryParams.getQueryCode());
    }
    @Override
    public Object queryAfter(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL ssrDynamicSQL, Map<String, Object> map, Object data) {
        return data;
    }
}
```
* 拦截方法介绍
    * preHandle 返回true则继续执行，返回false则不执行该查询。
    * queryBefore 查询前参数处理。可以添加新的查询参数或者修改前端传来的参数。例如添加当前登录用户的id
    * queryAfter 查询结果处理，例如屏蔽敏感信息。

#### 2.3.1.1 脚本拦截器
* 可以将数据处理写到数据库里，不需要编写java代码，可动态修改。默认实现了三种脚本处理。

#### 2.3.1.2 自定义拦截器
* 继承AbstractQueryJavaIntercept
* 重写里面的三个方法
* 加上`@Component`注解交给spring管理
* 加上`@QueryCode`注解，根据queryCode拦截，支持正则匹配

### 2.3.2 数据处理脚本
#### JavaScript
#### Python
#### Groovy
#### 自定义脚本脚本
* 继承AbstractQueryScriptIntercept
* 重写executeScript
* 加上`@Component`注解交给spring管理
* 加上`@ConditionalOnProperty`注解，按配置条件注入
* 在配置文件上加上注解里的条件

### 2.3.3 多数据源
#### 多数据源配置
```yaml
ssr:
  multi-data-source:
    数据源名称1:
      username: database username
      password: database password
      url: database url
      driver-class-name: database driver
      ……
    数据源名称2:
      username: database username
      password: database password
      url: database url
      driver-class-name: database driver
      ……
    ……
```
#### 多数据源使用
* 数据库表     

![query-user](file/img/2.3.3-1.png)   

DATA_SOURCE_NAME列为空，则使用默认数据源。

* 自动生成
queryCode：[表名/类名]:[generateByTable/generateByEntity]:[数据源的名称]   


### 2.3.4 sql生成
不需要在表中插入查询的sql数据  

#### 2.3.4.1 根据表名生成
* 在配置文件夹中加入ssr.enable.generate-by-table=true
* queryCode写法规则：[表名]:[generateByTable]:[数据源的名称]
* 数据源不写则使用默认数据源
* selectFields：查询字段，多个用英文逗号分隔   
![query-user](file/img/2.3.4-1.png)   

#### 2.3.4.2 根据实体名生成
* 在配置文件夹中加入ssr.enable.generate-by-entity=true
* queryCode写法规则：[类名]:[generateByEntity]:[数据源的名称]
* 数据源不写则使用默认数据源
* 类似需要加上注解`@TableName`,如果不加则按驼峰规则转换成表名
* selectFields：查询字段，多个用英文逗号分隔   
![query-user](file/img/2.3.4-2.png)   


### 2.3.5 缓存
只缓存SSRDynamicSQL表的查询数据。

#### 2.3.5.1 内置缓存
* 内部实现的缓存
    * ehcache
    * redis
* 使用
需要手动添加配置，指定使用的缓存，例如使用redis：ssr.cache.type=redis

#### 2.3.5.2 自定义缓存
* 实现接口SSRCache
* 加上注解`@Component`交给spring管理
* 也可以添加上配置条件，加上注解`@ConditionalOnProperty(prefix = "ssr", name = "cache.type", havingValue = "自定义缓存key")`

### 2.3.5 orm框架
#### 2.3.5.1 MyBatis

#### 2.3.5.2 hibernate

#### 2.3.5.3 自定义orm框架
* 主要是实现接口QueryORM，也可以同时实现接口QueryORM跟继承AbstractQueryORM，AbstractQueryORM类默认实现了一些方法，不需要再实现。
* 加上注解`@Component`交给spring管理


### 2.3.6 自定义SSRDynamicSQL表名
可以添加配置：ssr.dynamicSql.TableName=自定义SSRDynamicSQL表名

### 2.3.7 bean说明文档
#### 2.3.7.1 QueryParams

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|queryCode|String|对应数据库里的QUERY_CODE。如是自动生成sql模式，格式为[表名/类名全名]:[generateByTable/generateByEntity]:[数据源的名称]|必填|
|params|Map<String, Object>|查询参数。key为sql里的占位符，value为值|非必填|
|sort|LinkedHashMap<String, String>|排序。key为排序的字段，value为正序或者倒序|非必填|
|queryResultModel|boolean|是否返回封装类型。true为查询结果返回QueryResult封装的结果集|非必填|
|generateSort|boolean|是否生成排序。如果为false。则设置了sort属性也不会排序。|非必填|
|condition|List<QueryCondition>|自动生成sql where 条件用。因为是自动生成sql语句，所以查询条件需要通过此参数来设置。|非必填|
|selectFields|String|自动生成sql用，查询字段，多个用英文逗号分隔。默认为*。|非必填|
|pageNo|int|分页用，第几页。|非必填|
|pageSize|int|分页用，一页多少条。-1则为查询全部。|非必填|
|generatePage|boolean|是否生成分页。默认为true。|非必填|

#### 2.3.7.2 QueryCondition

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|queryCode|String|对应的queryCode|必填|
|value|Object|查询条件的值|必填|
|connect|String|查询条件|必填|
|operational|String|条件跟条件关系操作符|非必填（单个条件）|
|groupId|String|分组查询分组id|分组查询必填|
|groupConnect|String|分组查询|分组查询必填|

#### 2.3.7.3 QueryInfo

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|||||
|||||
|||||
|||||
|||||
|||||
|||||


#### 2.3.7.4 QueryResult

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|||||

#### 2.3.7.5 QueryRule

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|||||

#### 2.3.7.6 QuerySQL

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|||||


#### 2.3.7.7 SSRDynamicSQL

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|||||

### [使用示例](https://gitee.com/natsuki_kining/ultra-rare)