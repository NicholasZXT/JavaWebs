package pers.zxt.springboot.swagger.controller;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;  // 这个和上面的冲突了，只能使用全限定类名
import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;

import pers.zxt.springboot.swagger.domain.User;
import pers.zxt.springboot.swagger.domain.Client;
import pers.zxt.springboot.swagger.domain.QueryPage;

/**
 * 专门用于展示 Springdoc 集成 Swagger 的 Controller。
 * SpringDoc 也是使用的 Swagger 提供的原生注解，参见如下的官方文档：
 * [Swagger 2.X Annotations](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations)
 * Controller里常用注解如下：
 *   - @Operation: 最重要的注解，用于方法上标识需要输出文档的视图函数。
 *       它里面也有如下注解对应的参数，可以直接设置对应的属性，不过优先级没有下面直接使用注解的方式高。
 *   - @Parameter
 *   - @io.swagger.v3.oas.annotations.parameters.RequestBody
 *   - @ApiResponse / @ApiResponses
 *   - @Tag
 *   - @Content
 *   - @Schema
 */
@RestController(value = "Swagger Basic Controller")
@RequestMapping(value = "/swagger/basic")
@Tag(name = "基本使用", description = "SpringDoc基本使用")
public class SpringDocController {

    @Operation(
        method = "Get",
        tags = {"用户管理"},
        summary = "获取所有用户",
        description = "返回系统所有用户",
        responses = {
            @ApiResponse(
                headers = {
                    @Header(name = "X-Rate-Limit", description = "Call Limit", schema = @Schema(implementation = Integer.class))
                },
                description = "用户列表",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class),
                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                ),
                responseCode = "200"
            )
        }
    )
    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return Arrays.asList(
            new User(1L, "Xiao Ming", "xiaoming@example.com", Arrays.asList("ADMIN", "USER")),
            new User(2L, "Xiao Hong", "xiaohong@example.com", Arrays.asList("ADMIN", "User"))
        );
    }

    @Operation(
        method = "Get",
        tags = {"用户管理"},
        summary = "根据ID获取用户",
        description = "根据ID获取用户",
        // 用于集中申明参数
        parameters = {
            @Parameter(
                name = "userId",  // 必须和方法的参数名一致
                description = "待查询的用户ID",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(implementation = Long.class),
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                description = "用户",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class)
                ),
                responseCode = "200"
            )
        }
    )
    @GetMapping("/user/{id}")
    public User getUserById(
        // 也可以在这里使用 @Parameter 注解
        //@Parameter(description = "待查询的用户ID", required = true, example = "1")
        @PathVariable("id") Long userId
    ) {
        return new User(userId, "SomeUser", "someuser@example.com", Arrays.asList("USER", "GUEST"));
    }

    @Operation(
        method = "Post",
        tags = {"用户管理"},
        summary = "创建用户",
        description = "创建用户",
        // 定义请求体 schema，也可以在下面的方法参数里定义
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "用户ID",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class)
                )
            ),
        responses = {
            @ApiResponse(
                description = "用户",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = User.class)
                ),
                responseCode = "200"
            )
        }
    )
    //@SecurityRequirement(name = "bearer-key") // 需要 JWT 认证
    @PostMapping(value = "/user/create")
    public User createUser(
        // 也可以在这里使用注解
        //@io.swagger.v3.oas.annotations.parameters.RequestBody()
        @RequestBody User user
    ) {
        return user;
    }

}
