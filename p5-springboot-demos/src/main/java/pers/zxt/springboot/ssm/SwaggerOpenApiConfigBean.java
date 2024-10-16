package pers.zxt.springboot.ssm;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springdoc.core.GroupedOpenApi;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import java.util.HashMap;

/**
 * Springdoc 配置 Swagger 的第 1 种方式，使用 Bean 对象返回配置。
 * 此时使用的是 io.swagger.v3.oas.models 里提供的各个 **类** 来构造 Swagger 所需要的信息，这个也是注解方式的底层实现。
 */
//@SpringBootConfiguration
public class SwaggerOpenApiConfigBean {
    /***
     * 构建Swagger3.0文档说明，注意，这里并不包含每个接口的配置
     * @return 返回 OpenAPI 对象
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // 联系人信息(contact)，构建API的联系人信息，用于描述API开发者的联系信息，包括名称、URL、邮箱等
        // name：文档的发布者名称 url：文档发布者的网站地址，一般为企业网站 email：文档发布者的电子邮箱
        Contact contact = new Contact()
                .name("DocAuthor")                  // 作者名称
                .email("example@email.com")         // 作者邮箱
                .url("https://example.com")         // 介绍作者的URL地址
                .extensions(new HashMap<String, Object>()); // 使用Map配置其他信息（如key为"name","email","url"）

        // 授权许可信息(license)，用于描述API的授权许可信息，包括名称、URL等；假设当前的授权信息为Apache 2.0的开源标准
        License license = new License()
                .name("Apache 2.0")                                         // 授权名称
                .url("https://www.apache.org/licenses/LICENSE-2.0.html")    // 授权信息
                .identifier("Apache-2.0")                                   // 标识授权许可
                .extensions(new HashMap<String, Object>()); // 使用Map配置其他信息（如key为"name","url","identifier"）

        //创建Api帮助文档的描述信息、联系人信息(contact)、授权许可信息(license)
        Info info = new Info()
                .title("Swagger3.0 (Open API) 框架学习示例文档")      // Api接口文档标题（必填）
                .description("Some Description")                   // Api接口文档描述
                .version("1.2.1")                                  // Api接口版本
                .termsOfService("https://example.com/")            // Api接口的服务条款地址
                .license(license)                                  // 设置联系人信息
                .contact(contact);                                 // 授权许可信息

        // 返回信息
        return new OpenAPI()
                .openapi("3.0.1")  // Open API 3.0.1(默认)
                .info(info);       // 配置Swagger3.0描述信息
    }
}
