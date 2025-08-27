
之前使用mybatis所必须的对象有：
1. 数据源dataSource，这个是在 `mybatis.xml` 配置文件中通过 <environment> -> <dataSource> 子标签直接配置的数据库连接要素
2. `SqlSessionFactory`对象，该对象是通过 `SqlSessionFactoryBuilder().build(inputStream)` 创建的，之后通过此对象的 openSession() 
   方法获取 SqlSession对象
3. 通过 `SqlSession.getMapper(StudentDao.class)` 方法获取 DAO接口的代理，最后通过DAO接口的方法操作数据库

在spring中集成mybatis时，也需要上面三个对象，但是创建方式和上面略有不同：
1. 数据源dataSource 不能在 `mybatis.xml` 配置文件中配置了，需要单独使用一个数据源对象，并交给spring容器管理
   + dataSource类必须是 `javax.sql.DataSource` 接口的实现类
   + 配置文件中的数据库连接要素配置不生效，原因见下面
2. 不再使用 `SqlSessionFactoryBuilder()` 创建 `SqlSessionFactory` 对象，而是改为使用 `SqlSessionFactoryBean`
   + `SqlSessionFactoryBean`类 内部会创建SqlSessionFactory对象。
   + `SqlSessionFactoryBean`类 是 mybatis-spring 包中提供的，并且类的对象要交给spring容器管理。
   + `SqlSessionFactoryBean`对象 有一个 dataSource 属性，专门提供给spring通过set属性注入的方式设置数据源。
   + `SqlSessionFactoryBean`对象 还有一个 configLocation 属性，用于读取mybatis.xml配置文件，但是**它会忽略其中的数据源和事务管理配置，
     自己会重新配置**，这就是为什么需要一个单独dataSource对象的原因。
3. 获取DAO代理对象时，也不通过 `SqlSession.getMapper()` 获取了，而是通过 `MapperScannerConfigurer`对象 来获取
   + `MapperScannerConfigure`r类 也是 mybatis-spring 包中提供的，并且类的对象也要交给spring容器管理。
   + `MapperScannerConfigurer`类 会直接扫描 dao 包里所有的 mapper 配置文件，生成所有 Dao接口的 代理对象，放入spring容器中。