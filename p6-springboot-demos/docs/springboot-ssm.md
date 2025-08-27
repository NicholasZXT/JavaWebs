SpringBoot整合mybatis几个关键点：

1. 引入 mybatis-spring-boot-starter 和 MySQL 驱动
2. 告诉 mybatis 哪里去寻找 dao 接口，有两种方式：
   1. 在 启动入口类上使用 `org.mybatis.spring.annotation.MapperScan` 注解，指定要扫描的包
   2. 在 `StudentDao` 接口上使用 `org.apache.ibatis.annotations.Mapper` 注解
3. 在 application.yml 中配置数据源
