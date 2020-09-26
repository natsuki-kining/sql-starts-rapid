# sql-starts-rapid

-----
#目录
* [一、介绍](#一、介绍)
    * [1.1 简称：ssr](#1.1简称：ssr)
* [使用说明](#使用说明)
    * [2.1快速体验](#2.1快速体验)
----
# 一、介绍
## 1.1简称：ssr（可以期待下个项目->[ur](https://gitee.com/natsuki_kining/ultra-rare )）

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

# 使用说明

## 2.1快速体验（运行的是test-ssr-mybatis项目）

* ssr_user中的数据     

### 2.1.1 简单sql查询

* 在ssr_dynamic_sql 表中新增数据：
```sql
INSERT INTO `ssr_dynamic_sql` (`ID`, `QUERY_CODE`, `SQL_TEMPLATE`) VALUES ('1', 'query-user', 'select * from ssr_user');
``` 

* postman请求   

### 2.1.2 带查询条件查询
* 使用freemarker为sql添加查询条件
```sql
INSERT INTO `ssr_dynamic_sql` (`ID`, `QUERY_CODE`, `SQL_TEMPLATE`) VALUES ('1', 'query-user', 'select * from ssr_user \r\nwhere 1=1\r\n<#if name??><#noparse>\r\nand NAME like CONCAT(\'%\',#{name},\'%\')\r\n</#noparse></#if>\r\n<#if userName??><#noparse>\r\nand USER_NAME like CONCAT(\'%\',#{userName},\'%\')\r\n</#noparse></#if>\r\n<#if code??><#noparse>\r\nand `CODE` = #{code}\r\n</#noparse></#if>');
```
> 其中`<#if>`为添加判断，`<#noparse>`为freemarker`#{}`转义
* postman请求      
> 输出的查询sql语句为 select * from ssr_user  where 1=1 and NAME like CONCAT('%',?,'%')
* postman请求      
> 输出的查询sql语句为 select * from ssr_user  where 1=1 and NAME like CONCAT('%',?,'%') and USER_NAME like CONCAT('%',?,'%')

### 2.1.3 返回封装的类型
* 在query接口里传入需要转换的类型
```
    @PostMapping("queryUser")
    public Object queryUser(@RequestBody QueryParams queryParams) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.natsuki_kining.ssr.test.entity.SSRUser");
        List<?> result = this.query.queryList(queryParams, aClass);
        return result;
    }
```
* postman请求  

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
输出的sql语句：
> select * from ssr_user LIMIT 0,2

* 排序
设置sort参数即可    
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

DATA_SOURCE_NAME列为空，则使用默认数据源。

* 自动生成
queryCode：[表名/类名]:[generateByTable/generateByEntity]:[数据源的名称]   


### 2.3.4 sql生成
不需要在表中插入查询的sql数据  

#### 2.3.4.1 根据表名生成
* 在配置文件夹中加入ssr.enable.generate-by-table=true
* queryCode写法规则：表名:[generateByTable][:][数据源的名称]  ([]可不填)  
    * 例如： ssr_user:
* 数据源不写则使用默认数据源
* selectFields：查询字段，多个用英文逗号分隔   

#### 2.3.4.2 根据实体名生成
* 在配置文件夹中加入ssr.enable.generate-by-entity=true
* queryCode写法规则：类全路径:[generateByEntity][:][数据源的名称]  ([]可不填)
    * 例如： com.natsuki_kining.ssr.test.entity.SSRUser:
* 数据源不写则使用默认数据源
* 类似需要加上注解`@TableName`,如果不加则按驼峰规则转换成表名
* selectFields：查询字段，多个用英文逗号分隔   

#### 2.3.4.3 sql生成自定义查询条件
logicalOperator：默认值"AND"  
relationalOperator：默认值“=”
##### 2.3.4.2.1 根据查询参数默认规则生成查询条件   
* JSON：
```json
    {
        	"queryCode":"ssr_user:",
            "params":{
                "code":"227",
                "user_name":"李会问"
            }
    }
```
* SQL：
```sql
    SELECT * FROM ssr_user T1 WHERE 1=1 AND T1.CODE = ?  AND T1.USER_NAME = ?
```

* 参数
> Parameters : [227, 李会问]    
> Types : [VARCHAR, VARCHAR]

* 结果
```json
    [
        {
            "password": "651860cc8dd74513ae606a02e656a311",
            "code": "227",
            "user_name": "李会问",
            "name": "11b3c4dbc8424171b09cc3d0f280f4bb",
            "id": 227
        }
    ]
```

##### 2.3.4.2.2 自定义条件查询简单示例：右模糊
* JSON
```json
    {
        "queryCode":"ssr_user:",
        "condition":[
            {
                "fieldName":"user_name",
                "value":"李机",
                "relationalOperator":"rl"
            }
        ]
    }
```

* SQL
```sql
    SELECT * FROM ssr_user T1 WHERE 1=1 AND T1.USER_NAME LIKE concat(? ,'%')  
```

* 参数
> Parameters : [李机]   
> Types : [VARCHAR]

* 结果
```json
    [
        {
            "password": "90c1b9a427e447da982a98641300d3d8",
            "code": "3114",
            "user_name": "李机夫",
            "name": "4af4bfeb0f8c4b85826c22c147836927",
            "id": 3114
        },
        {
            "password": "4689d61c47ef48b2808bc187fb029c21",
            "code": "3240",
            "user_name": "李机写",
            "name": "7351a02b824e4ce5849f4dd2da6ecba0",
            "id": 13240
        }
    ]
```


##### 2.3.4.2.3 多条件：   
* JSON
```json
    {
        "queryCode":"ssr_user:",
        "condition":[
            {
                "fieldName":"user_name",
                "value":"李",
                "relationalOperator":"rl"
            },
            {
                "fieldName":"user_name",
                "value":"向",
                "relationalOperator":"ll"
            }
        ]
    }
```

* SQL
```sql
    SELECT * FROM ssr_user T1 WHERE 1=1 AND T1.USER_NAME LIKE concat(? ,'%')  AND T1.USER_NAME LIKE concat('%',? )
```

* 参数
> Parameters : [李, 向]   
> Types : [VARCHAR, VARCHAR]

* 结果
```json
    [
        {
            "password": "b90d9dfbd9bb42baa19a8559eebcc7fb",
            "code": "267",
            "user_name": "李口向",
            "name": "b06bdd2c8f5a4c8fb25e5797597992f3",
            "id": 267
        }
    ]
```

##### 2.3.4.2.4 分组查询
```json
    {
        "queryCode":"ssr_user:",
        "condition":[
            {
                "condition":[
                    {
                        "fieldName":"user_name",
                        "value":"李",
                        "relationalOperator":"rl"
                    },{
                        "fieldName":"user_name",
                        "value":"问",
                        "relationalOperator":"ll"
                    }
                ]
            },
            {
                "logicalOperator":"or",
                "condition":[
                    {
                        "fieldName":"password",
                        "value":"aa",
                        "relationalOperator":"ll"
                    },{
                        "fieldName":"password",
                        "value":"ea",
                        "relationalOperator":"rl"
                    }
                ]
            }
        ]
    }
```
 
* SQL
```sql
    SELECT * FROM ssr_user T1 WHERE 1=1 AND ( T1.USER_NAME LIKE concat(? ,'%')  AND T1.USER_NAME LIKE concat('%',? )  ) OR ( T1.PASSWORD LIKE concat('%',? )  AND T1.PASSWORD LIKE concat(? ,'%')  ) 
```
* 参数
> Parameters : [李, 问, aa, ea]    
> Types : [VARCHAR, VARCHAR, VARCHAR, VARCHAR]

* 结果集
```json
    [
        {
            "password": "651860cc8dd74513ae606a02e656a311",
            "code": "227",
            "user_name": "李会问",
            "name": "11b3c4dbc8424171b09cc3d0f280f4bb",
            "id": 227
        },
        {
            "password": "ea72db827a9247b19fe643b0c489b0aa",
            "code": "925",
            "user_name": "吕先真",
            "name": "ef0ba32ee43e485bb476fa197d24a747",
            "id": 925
        },
        {
            "password": "d905182a2d394ade8d49193e9de9c582",
            "code": "1782",
            "user_name": "李十问",
            "name": "0ea6786b036f47f2a2c42973fa12139a",
            "id": 1782
        }
    ]
```

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
|fieldName|String|查询条件的字段名|必填|
|value|Object|查询条件的值|必填|
|logicalOperator|String|逻辑运算符|非必填。取值例如：AND OR 等|
|relationalOperator|String|关系运算符|非必填。取值例如：like = != > < >= >= 等|
|condition|QueryCondition|分组查询条件|非必填|

#### 2.3.7.3 QueryInfo

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|querySQL|QuerySQL|查询的sql分组类|非必填|
|queryStartTime|Long|查询开始时间|非必填|
|queryEndTime|Long|查询结束时间|非必填|


#### 2.3.7.4 QueryResult

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|result|Object|查询结果集|必填|
|code|Integer|查询状态码|必填|
|message|String|查询响应信息|必填|

#### 2.3.7.5 QueryRule

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|queryCode|String|查询code|必填|
|dynamicSql|SSRDynamicSQL|SSRDynamicSQL|非必填|
|queryCodeType|QueryCodeType|查询规则类型|必填|
|queryCodeMap|Map<String, QueryRule>|查询规则|非必填|

#### 2.3.7.6 QuerySQL

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|processedSQL|String|查理过sql|非必填|
|executeSQL|String|当前执行的SQL|非必填|
|simpleSQL|String|原SQL|必填|

#### 2.3.7.7 SSRDynamicSQL

|属性名|属性类型|属性意思|是否必填|
|-----|-------|-------|------|
|id|String|主键|是|
|queryCode|String|查询的code。不允许使用英文逗号“,”跟冒号“:”|是|
|queryName|String|查询的名称|否|
|queryType|String|查询的类型|否|
|dataSourceName|String|数据源名称|否|
|sqlTemplate|String|sql模板|否|
|resultType|String|返回类型，多个用逗号分割。queryCode:class name|否|
|beforeScript|String|查询之前处理脚本|否|
|afterScript|String|查询之后处理脚本|否|
|version|Integer|版本号|否|
|delFlag|Integer|是否删除。1：是，0：否|否|
|orderNum|Integer|排序编号。正序。|否|
|createName|String|创建人名称|否|
|createId|String|创建人id|否|
|createTime|Date|创建时间|否|
|updateName|String|修改人名称|否|
|updateId|String|修改人id|否|
|updateTime|Date|修改时间|否|
|String|remark|备注|否|

#### 2.3.7.8 Query
```
    /**
     * 查询返回Object
     *
     * @param queryParams 查询参数
     * @return Class<T> clazz 转换的类型
     */
    <T> Object query(QueryParams queryParams, Class<T> clazz);

    /**
     * 查询返回Object
     *
     * @param queryParams 查询参数
     */
    Object query(QueryParams queryParams);

    /**
     * 查询返回map list
     *
     * @param queryParams 查询参数
     * @return map的集合
     */
    List<Map> queryList(QueryParams queryParams);

    /**
     * 查询返回泛型list
     *
     * @param queryParams 查询参数
     * @param clazz       转换的类型
     * @param <T>         泛型
     * @return 泛型的集合
     */
    <T> List<T> queryList(QueryParams queryParams, Class<T> clazz);

    /**
     * 查询返回QueryResult封装类型
     *
     * @param queryParams
     * @param <T>
     * @return
     */
    <T> QueryResult queryResult(QueryParams queryParams);

    /**
     * 查询返回QueryResult封装类型
     *
     * @param queryParams
     * @param clazz
     * @param <T>
     * @return
     */
    <T> QueryResult queryResult(QueryParams queryParams, Class<T> clazz);
```

#### 2.3.7.9 注解
* FieldName 用于实体里的属性，标注对应的数据库字段名。
* TableName 用于实体里的属性，标注对应的数据库表名。
* QueryCode 用于拦截器类上，如果当前查询的queryCode跟注解里的一样，则会拦截。

### 2.3.8 项目工程介绍
#### 2.3.8.1 ssr-core
主要的实现核心

#### 2.3.8.2 ssr-hibernate
实现hibernate的查询

#### 2.3.8.3 ssr-mybatis
实现mybatis的查询

#### 2.3.8.4 test-ssr-hibernate
以hibernate作为orm框架的测试项目

#### 2.3.8.5 test-ssr-mybatis
以mybatis作为orm框架的测试项目


### [使用示例](https://gitee.com/natsuki_kining/ultra-rare)
