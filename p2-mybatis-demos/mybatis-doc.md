mybatis开发步骤：

1. 编写业务实体类   
  `domain.Student`是一个Bean对象

2. 编写DAO接口和实现类   
  + 接口为`dao.StudentDao`，其中定义了`Student`对应的表的操作方法
  + 实现类为`dao.impl.StudentImpl`，具体实现接口定义中的方法，它会使用下面定义的mapper文件中的编写的SQL语句
  + 上面的实现类可以省略，通过mybatis的动态代理来实现，不用手动编写

3. 编写DAO接口对应的mapper映射文件   
  接口`dao.StudentDao`对应的映射文件为`dao.StudentDao.xml`，其中定义了接口每个方法的对应使用的SQL语句，每个SQL语句都有一个对应的ID

4. 编写mybatis主配置文件
  在`resource`目录下创建`mybatic.xml`配置文件，主要配置如下的项目：
  + 配置数据库的连接信息，比如用户密码，驱动什么的
  + 配置用到的mapper映射文件

5. 代码使用   
  + 代码中创建`SqlSessionFactoryBuilder`对象，读取`mybatis.xml`配置文件
  + 通过SQL对应的ID执行mapper映射文件中的SQL语句
