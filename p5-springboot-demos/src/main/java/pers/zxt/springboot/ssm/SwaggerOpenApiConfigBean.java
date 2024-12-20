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
 * 此种方式又分为两种：
 *   1. 使用 Swagger 提供的原生入口类： OpenAPI 类
 *   2. 使用 SpringDoc 封装好的入口类： GroupedOpenApi 类
 * 此种方式必须要使用 @SpringBootConfiguration 注解，否则 SpringDoc 无法获取里面的 @Bean 返回的配置对象。
 * 默认访问URL: http://localhost:8100/swagger-ui/index.html
 *
 * Swagger原生的使用方式参考如下的官方文档：
 * + [Swagger 2.X Getting started](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Getting-started#quick-start)
 *   快速入门
 * + [Swagger 2.X Integration and Configuration](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Integration-and-Configuration)
 *   介绍了Swagger的集成和配置方式，原生的Swagger使用起来比较复杂，要通过配置对应 Servlet 启动的方式，也介绍了配置文件的使用。
 * + [Swagger 2.X Annotations](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations)
 *   介绍了 Swagger 提供的一系列注解，这个比较有用。
 * 总结如下：
 *  1. Swagger原生使用方式要通过配置HttpServlet的方式，比较复杂
 *  2. Swagger配置文件 openapi.yml/openapi.json 也需要自己设置读取方式（Servlet启动加载，或者代码中读取），不适用和SpringBoot集成。
 *     **最重要的一点，使用 SpringDoc 集成时，SpringDoc 似乎并不会自动读取 resource 目录下配置文件，而是忽略它**
 *  3. **Swagger原生提供的注解很有用，因为 SpringDoc 也是基于原生注解来配置的，并没有提供其他注解**
 *  推荐使用 SpringDoc 提供的配置方式来对Swagger-UI进行定制，也就是在 SpringBoot 的配置文件中，以 `springdoc` 开头的配置项目。
 */
@SpringBootConfiguration
public class SwaggerOpenApiConfigBean {
    /***
     * 使用 Swagger3.0 原生提供的 OpenAPI 类来构建基本信息配置（这里并不包含每个接口的文档内容）。
     * 此时使用的是 io.swagger.v3.oas.models 里提供的各个 **类** 来构造 Swagger 所需要的信息，这个也是注解方式的底层实现。
     * 注意，这种返回 OpenAPI Bean 的方式是 SpringDoc 支持的，而不是 io.swagger.core.v3 原生的使用方式。
     * 原生使用方式里，还需要对 OpenAPI 对象进行封装和配置执行环境，这些工作 SpringDoc 都帮开发者配置好了。
     * @return OpenAPI 对象
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
                .title("Swagger3.0 (Open API) 框架")                // Api接口文档标题（必填）
                .description("Some Description. **Generated by Bean**.")   // Api接口文档描述
                .version("1.2.1")                                  // Api接口版本
                .termsOfService("https://example.com/")            // Api接口的服务条款地址
                .license(license)                                  // 授权许可信息
                .contact(contact);                                 // 设置联系人信息

        // 返回 OpenAPI 配置类
        OpenAPI openApi = new OpenAPI();
        openApi.openapi("3.0.1")  // 设置 Open API版本，默认就是 3.0.1
                .info(info);      // 配置 Swagger 描述信息
        return openApi;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // 下面使用 SpringDoc 提供的 OpenAPI 配置只会在 对应Group页面 覆盖上面 Swagger 原生的配置方式
    /**
     * 使用 SpringDoc 提供的 GroupedOpenApi 来构建文档配置，它提供了API文档分组的功能。
     * 所谓分组，就是在 Swagger-UI 的接口文档页面右上方，提供了可以切换的选项卡，用于展示不同组的接口文档信息，每个组相当于一个单独的 OpenAPI 页面。
     * 此时使用的是 org.springdoc.core 里提供的 **类** 来构造 Swagger 所需要的信息。
     * GroupedOpenApi 本身不是 OpenAPI 的子类，但是它有一个 List<OpenApiCustomiser> openApiCustomisers 实例变量，其中的
     * OpenApiCustomiser 就是一个接收 OpenAPI 对象并进行各种配置的接口。
     * @return GroupedOpenApi 对象
     */
    @Bean
    public GroupedOpenApi GroupedOpenApiForG1(){
        // 和上面的 OpenAPI 对象不同，这里使用了建造者模式来创建
        GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
                .group("group1")
                .displayName("Group-1")
                // 这里使用了lambda表达式来构造一个 OpenApiCustomiser接口的匿名实现类，在里面对 OpenAPI 配置对象设置了标题
                // 可以将上面 customOpenAPI() 方法里的自定义 OpenAPI 页面信息的逻辑放在这里
                // 不过需要注意的是，这里设置的只是当前 Group 页面的 OpenAPI 配置 -------- KEY
                .addOpenApiCustomiser(openApi -> openApi.openapi("3.0.2").info(new Info().title("Title From GroupedOpenApiForG1")))
                // 设置此分组接口要扫描的package
                .packagesToScan("pers.zxt.springboot.ssm.controller")
                // 设置此分组接口要排除的package
                //.packagesToExclude("")
                // 设置此分组接口匹配的URL路径
                .pathsToMatch("/springdoc/g1/**")
                // 设置此分组接口需要排除的URL路径
                .pathsToExclude("/students/**")
                // API接收的消息格式
                .consumesToMatch(new String[]{"application/json", "application/xml", "application/x-www-form-urlencoded"})
                // API返回的消息格式
                .producesToMatch(new String[]{"application/json"})
                .build();
        return groupedOpenApi;
    }

    @Bean
    public GroupedOpenApi GroupedOpenApiForG2(){
        GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
                .group("group2")   //  group的名称不能重复，这是唯一标识字段，并且会被用于URL中
                .displayName("Group-2")
                // Group2 如果不自定义 OpenAPI，那么就会使用上面 customOpenAPI() 方法返回的 OpenAPI 对象的配置
                //.addOpenApiCustomiser(openApi -> openApi.openapi("3.0.3").info(new Info().title("Title From GroupedOpenApiForG2")))
                .packagesToScan("pers.zxt.springboot.ssm.controller")
                //.packagesToExclude("")
                .pathsToMatch("/springdoc/g2/**")
                .pathsToExclude("/students/**")
                .build();
        return groupedOpenApi;
    }

    @Bean
    public GroupedOpenApi GroupedOpenApiForBasic(){
        // 这里先增加一个 GroupedOpenAPI，专门用于对应 SwaggerController 里的请求处理
        GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
                .group("basic")
                .displayName("Group-Basic")
                .packagesToScan("pers.zxt.springboot.ssm.controller")
                // 注意，下面的URL路径匹配中，必须要使用两个 *，只用一个 * 表示的是只匹配下一层的任意URL，无法匹配到多层URL -------- KEY
                //.pathsToMatch("/swagger/basic/*")
                .pathsToMatch("/swagger/basic/**")
                .pathsToExclude("/students/**")
                .build();
        return groupedOpenApi;
    }
}
