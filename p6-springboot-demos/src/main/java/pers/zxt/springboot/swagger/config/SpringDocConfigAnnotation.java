package pers.zxt.springboot.swagger.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Springdoc 配置 Swagger-UI 的第 2 种方式，使用 @OpenAPIDefinition 注解。
 * 此时使用的是 io.swagger.v3.oas.annotations 里提供的各个 **注解** 来构造 Swagger 所需要的信息。
 * 默认访问URL: http://localhost:8100/swagger-ui/index.html
 *
 * 此种方式没有 OpenAPI Bean 那么灵活（不能配置components，无法动态设置属性），但胜在快速简洁。
 * 并且似乎 **注解配置优先级 > Bean配置优先级** 。
 *
 * 此种方式下， @OpenAPIDefinition 注解通常是放在 @SpringBootApplication 注解的启动类上面。
 */
//@SpringBootApplication
@OpenAPIDefinition(
    // API的基本信息，包括标题、版本号、描述、联系人等
    info = @Info(
        title = "Swagger-UI-3.0(OpenAPI) - From Annotation",       // Api接口文档标题（必填）
        description = "Some Description from **Annotation**.",     // Api接口文档描述
        version = "2.0.0",                                         // Api接口版本
        termsOfService = "https://term.annotation.example.com/",   // Api接口的服务条款地址
        contact = @Contact(                                        // 设置联系人信息
            name = "DocAuthorAnnotation",                          // 作者名称
            email = "annotation.example@email.com",                // 作者邮箱
            url = "https://annotation.example.com/"                // 介绍作者的URL地址
        ),
        license = @License(
            name = "Apache 2.0",                                       // 授权名称
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"   // 授权信息
        )
    ),
    // 表示服务器地址或者URL模板列表，多个服务地址随时切换（只不过是有多台IP有当前的服务API）—— 这个参数会影响Swagger UI里的请求地址
    servers = {
        @Server(url = "http://localhost:8100/", description = "本地服务")
    },
    // 外部文档信息
    externalDocs = @ExternalDocumentation(description = "外部文档链接（From Annotation）", url = "https://external.doc.example.com/"),
    // 扩展
    extensions = {
        @Extension(name = "some-extension", properties = @ExtensionProperty(name = "ext-prop-name", value = "ext-prop-value"))
    }
)
public class SpringDocConfigAnnotation {
    // 类定义体不重要，可以为空。实际中这个类通常是启动类。
}
