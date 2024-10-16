package pers.zxt.springboot.ssm;

import org.springframework.boot.SpringBootConfiguration;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Springdoc 配置 Swagger 的第 2 种方式，使用注解。
 * 默认访问URL: http://localhost:8081/swagger-ui/index.html
 */
@SpringBootConfiguration
@OpenAPIDefinition(
    // API的基本信息，包括标题、版本号、描述、联系人等
    info = @Info(
        title = "Swagger3.0 (Open API) 框架学习示例文档",       // Api接口文档标题（必填）
        description = "学习Swagger框架而用来定义测试的文档",      // Api接口文档描述
        version = "1.2.1",                                   // Api接口版本
        termsOfService = "https://example.com/",             // Api接口的服务条款地址
        contact = @Contact(
            name = "蚂蚁小哥",                            // 作者名称
            email = "xiaofeng@qq.com",                  // 作者邮箱
            url = "https://www.cnblogs.com/antLaddie/"  // 介绍作者的URL地址
        ),
        license = @License(                                                // 设置联系人信息
            name = "Apache 2.0",                                       // 授权名称
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"   // 授权信息
        )
    ),
    // 表示服务器地址或者URL模板列表，多个服务地址随时切换（只不过是有多台IP有当前的服务API）
    servers = {
        @Server(url = "http://192.168.2.235/demo/", description = "本地服务器一服务")
    },
    // 外部文档信息
    externalDocs = @ExternalDocumentation(description = "更多内容请查看该链接", url = "xxx")
)
public class SwaggerOpenApiConfigAnno {
}