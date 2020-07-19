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
    * 多数据源，可以设置动态多数据源。
* 第2.0版：
    * 新增查询计划，用于处理内部有多查询的情况。

#### 3 使用说明

##### 3.1 新增表 SSR_DYNAMIC_SQL
    脚本在file文件夹里

##### 3.2 引入依赖
    <dependency>
         <groupId>com.natsuki_kining.ssr</groupId>
         <artifactId>ssr-mybatis</artifactId>
         <version>1.0.0</version>
    </dependency>

##### 3.2 添加包扫描路径
    com.natsuki_kining
    
##### 3.2 添加包扫描路径
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
