# 目的
本demo主要演示多数据源的案例，apijson本身依赖的数据源也是单独配置的
另外，可以看到，完全用注解实现完整的能力，也就是说，适当改造apijson自己依赖的数据库是可以干掉的。

更基础的信息，见jdbc-demo下的文件

# 快速跑起来
1. 建立数据库，创建apijson必须以来的几张表
   * 执行apijson.sql文中的sql语句(apijson依赖表)
   * 执行biz_tables.sql（业务依赖表）

2. 使用postman导入文件multi-datasource.postman_collection.json

3. 运行程序，测试postman中的各个用例

# 代码解读
1. AbstractSQLConfig定义了从request中获取@datasource，根据该datasource，在MultiDemoSQLConfig中切换不同的getSQLSchema
2. MultiDemoSQLExecutor定义了如何根据request中获取@datasource，找到后端对应的数据源，从而获得数据库链接
3. MultiDemoDataSourceConfig利用spring的特性，从application.xml中诸如DataSource供使用
4. 通过在MultiDemoSQLConfig中设置别名，完成表的隐私化处理 TABLE_KEY_MAP.put(VipUser.class.getSimpleName(), "vip_user");

更多信息见jdbc-demo下的文件


TODO：增加函数校验的实例，今天不玩了