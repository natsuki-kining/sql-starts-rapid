# sql-starts-rapid

#### 一 介绍
##### 1.1 简称：ssr（才不会说就是为了缩写变成ssr才起的这个名，可以期待下个项目[ur](https://gitee.com/natsuki_kining/ultra-rare )）

##### 1.3 使用场景：
* 报表查询
* 不需要编写java代码便可构建一个SQL查询
* 经常需要修改SQL语句的查询

##### 1.4 优点：

* 可以在运行时修改sql，而不用重启项目。
* 可以快速的新增一个查询功能，而不用写任何代码。
* 不用再每个查询都新建一个controller

#### 2 各版本主功能说明
* 第1.0版：
    * 通过freemarker实现动态sql。
    * Java拦截器，用于处理查询前后的数据。
    * 脚本拦截器，可调用JavaScript，Python，Groovy等脚本语言处理数据。
    * 缓存，sql查询效率，提供sql优化建议等工具。
    * 多code查询，用于处理内部有多查询的情况。
    * 多数据源，可以设置动态多数据源。
* 第2.0版：
    * sql查询计划解析，分析sql查询计划，记录慢sql，提出修改建议。

#### 3 使用说明

##### 3.1 新增表 SSR_DYNAMIC_SQL
    脚本在file文件夹里

##### 3.2 引入依赖
    <dependency>
         <groupId>com.natsuki_kining.ssr</groupId>
         <artifactId>ssr-mybatis</artifactId>
         <version>1.0.0</version>
    </dependency>

##### 3.3 添加包扫描路径
    com.natsuki_kining
    
##### 3.4 添加包扫描路径
    在controller里注入Query，调用里面的方法。
    例如：
```java
@RestController
@RequestMapping("query")
public class QueryController {

    @Autowired
    private Query query;
    
    @PostMapping("page")
    public Object page(@RequestBody QueryParams queryParams) {
        return this.query.queryResult(queryParams);
    }

}
```

##### 3.5 Query接口方法说明
```
    /**
     * 查询返回map list
     *
     * @param queryParams 查询参数
     * @return map的集合
     */
    List<Map> query(QueryParams queryParams);

    /**
     * 查询返回泛型list
     *
     * @param queryParams 查询参数
     * @param clazz       转换的类型
     * @param <T>         泛型
     * @return 泛型的集合
     */
    <T> List<T> query(QueryParams queryParams, Class<T> clazz);

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

##### 3.6 QueryParams 参数说明
```
    queryCode:查询code。必须。表ssr_dynamic_sql的query_code字段
    params：传递给sql的查询参数。key->property,value->queryValue
```