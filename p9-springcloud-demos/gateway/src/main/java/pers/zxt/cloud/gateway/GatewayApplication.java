package pers.zxt.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Gateway 模块的典型结构如下：
 *
 * gateway-service/
 * ├── src/main/java
 * │   └── com.example.gateway
 * │       ├── GatewayApplication.java
 * │       ├── filter/
 * │       │   ├── AuthGlobalFilter.java      ← 自定义过滤器
 * │       │   └── LogGlobalFilter.java
 * │       └── config/
 * │           └── CorsConfig.java            ← 跨域配置
 * ├── src/main/resources
 * │   └── application.yml                    ← 路由、服务发现、过滤器配置
 *
 * 没有 controller、service、domain 包。
 * 网关模块里不应当有业务代码，也不应持有任何业务状态或领域模型。
 * 网关模块最常见的几个操作是：
 *   1. 在 filter 包里编写各种过滤器代码
 *   2. 在 config 包里编写跨域配置
 *   3. 在 application.yml 中配置路由、Predicate、Filter等
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
