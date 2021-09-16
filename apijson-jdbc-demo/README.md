
# 参考项目
https://github.com/jerrylususu/apijson_todo_demo/blob/master/FULLTEXT.md

# 快速跑起来
1. 建立数据库，创建apijson必须以来的几张表
   * 执行apijson.sql文中的sql语句(apijson依赖表)
   * 执行todo.sql（业务依赖表）

2. 使用postman导入文件demo.postman_collection.json

3. 运行程序，测试postman中的各个用例

# 代码解读
1. apijson orm本身就一个orm工具，更好的一点是不需要编码即可完成相关的upad
2. 数据库链接相关配置在DemoSQLConfig中，如IP、Port、DataBase、user、password等信息
3. CommonController 定义了对数据库表的通用增删改查操作，UserController定义了自定义的入口操作，和传统的spring mvc没有差异。
4. 每个业务表的访问权限控制在Access表中，决定了CURD都对哪些角色开放，系统启动时读取该表写入内存 AbstractVerifier.ACCESS_MAP 中，执行时进行判决，数据变更需要重启服务。
5. 每个请求基于tag进行检查设置，检查规则保存在Request表中，系统启动时读取该表写入内存 AbstractVerifier.REQUEST_MAP 中，执行时进行判决，数据变更需要重启服务。
6. 对外公开的函数写入在表Function中， 系统启动时读取该表写入内存 AbstractFunctionParser.FUNCTION_MAP 中，执行时进行判决，数据变更需要重启服务。

# 一个简单的demo需要做哪些事情？
1. 创建apijson数据库及对应的系统表，初始化必要的数据；
2. 创建业务表，并根据业务表的权限设置Access表数据
3. 确定每个访问方法的request的规则，这个就是业务逻辑的体现
4. 创建继承于APIJSONSQLConfig实体类，用于确定数据库访问方式；创建继承于APIJSONFunctionParser的实体类，暴露对外的函数，并在Function中配置访问规则。
5. 创建继承于APIJSONController的实体类，进行post到restful的转换。

# 几点解读
1. apijson本质上完成了json到sql的转换，通过灵活的语法来生成sql。难点是orm对应的sql生成器。
2. jdbc方式，演示了系统表和业务表必须在一个库中的场景
3. 通过系统的三张表，控制了访问权限、请求格式校验、远程函数调用检验，这个思路还是不错的。

4. 几个优化点：
   * 通过注解实现这三张表，可以完全脱离数据库的使用。
     通过三个注解（MethodAccess、RequestStructure、FunctionDef），动态完成对ACCESS_MAP、REQUEST_MAP、FUNCTION_MAP的填充；
     全部使用注解，是可以完成所有动作的。
   * 函数强制绑定在一个APIJSONFunctionParser是不合适的，可以使用内存对象的方式搞定。
   
