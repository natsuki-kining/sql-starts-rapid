# sql-starts-rapid

#### 一 介绍
##### 1.1 简称：ssr（才不会说就是为了缩写变成ssr才起的这个名）

##### 1.2 优点：

* 可以在运行时修改sql，而不用重启项目。
* 可以快速的新增一个查询功能，而不用写任何代码。
* 不用再每个查询都新建一个control

#### 2 各版本说明
* 第1.0版：通过freemark实现动态sql。
* 第1.2版：新增查询计划，用于处理内部有多查询的情况。
* 第1.3版：新增Java拦截器，用于处理查询前后的数据。
* 第2.x版：修改查询前后拦截器，可调用JavaScript，Python，Groovy等脚本语言处理数据。
* 第3.x版：新增缓存，sql查询效率，提供sql优化建议等工具。

#### 3 编码设计

##### 3.1 顶层接口

* 数据源
* 拦截器
* 执行器

#### 4 运行流程

1. 通过前端提交的code从数据库（缓存）查询得到freemark的模板
2. 查询参数经过拦截器，调用相应的Java代码或者脚本处理参数传给下一步。
3. 根据参数和模板得到要执行的sql
4. 执行查询sql
5. 异步记录查询时间，跟查询的sql，用于做sql查询计划分析，可以关闭。
6. 查询得到的数据经过拦截器，调用相应的Java代码或者脚本处理参数传给下一步
7. 处理多查询：判断第一步的code是否包含多个code，如果是则获取下一个code，执行第一步。
8. 如若执行完全部的code，调用查询计划里的脚本处理数据。
9. 返回数据给前端。

#### 5.1 使用说明

1. 引入jar包
2. 新建数据库表，或者使用redis缓存
3. 修改配置，开启control。或者自己写一个control进行调用。