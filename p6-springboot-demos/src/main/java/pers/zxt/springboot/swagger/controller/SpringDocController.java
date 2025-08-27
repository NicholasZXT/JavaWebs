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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;

import pers.zxt.springboot.swagger.domain.Client;
import pers.zxt.springboot.swagger.domain.QueryPage;

/**
 * 专门用于展示 Springdoc 集成 Swagger 的 Controller。
 * SpringDoc 也是使用的 Swagger 提供的原生注解，参见如下的官方文档：
 * [Swagger 2.X Annotations](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations)
 * 常用注解如下：
 *   - @Operation: 最重要的注解，用于方法上标识需要输出文档的视图函数。
 *       它里面也有如下注解对应的参数，可以直接设置对应的属性，不过优先级没有下面直接使用注解的方式高。
 *   - @Parameter
 *   - @RequestBody
 *   - @ApiResponse / @ApiResponses
 *   - @Tag
 *   - @Content
 *   - @Schema
 */
@RestController(value = "Swagger Basic Controller")
@RequestMapping(value = "/swagger/basic")
public class SpringDocController {

    @Operation(
            method = "Get",
            summary = "Test Swagger Controller",
            description = "Some descriptions...",
            tags = {"client"},
            responses = {
                    @ApiResponse(
                            description = "List<String>",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/test")
    public List<String> testCase() {
        return Arrays.asList("First Content", "Second Content");
    }

    @Operation(
            method = "Get",
            summary = "Test Client",
            description = "Some descriptions...",
            tags = {"client"},
            responses = {
                    @ApiResponse(
                            description = "String",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/test/client")
    public String testClient(){
        Client client = new Client();
        client.setClientId("random-id");
        client.setClientName("random-name");
        return client.toString();
        //return client;
    }

    @Operation(
            method = "Get",
            summary = "Query one client by ID",
            description = "Some descriptions...",
            tags = {"client"},
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Client ID",
                            required = true,
                            // 参数解析位置
                            in = ParameterIn.PATH,
                            schema = @Schema(implementation = Integer.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            description = "Client",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    @GetMapping(value = "/client/{id}")
    public Client selectOneClient(
            // 获取 GET 请求中的路径参数
            @PathVariable(value = "id") String id
    ){
        System.out.println("id: " + id);
        Client client = new Client();
        client.setClientId(id);
        client.setClientName("Random");
        //return client.toString();
        return client;
    }

    @Operation(
            method = "Get",
            summary = "Query all clients by query param with @RequestParam.",
            description = "Some descriptions...",
            tags = {"client"}
    )
    @ApiResponse(
            description = "Client Bean",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))
    )
    @GetMapping(value = "/client/list/v1")
    public Map<String, Object> selectAllClientsV1(
            // 获取 GET 请求中的查询参数方式一，使用 @RequestParam 注解，@Parameter 是 SpringDoc 的参数注解
            @Parameter(name = "page", in = ParameterIn.QUERY, description = "page", required = false, schema = @Schema(implementation = Integer.class))
            @RequestParam(value = "page") int page,
            @Parameter(name = "size", in = ParameterIn.QUERY, description = "size", required = false, schema = @Schema(implementation = Integer.class))
            @RequestParam(value = "size") int size
    ){
        System.out.println("page: " + page + ", size: " + size);
        Map<String, Object> res = new HashMap<>();
        res.put("page", page);
        res.put("size", size);
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("id-1", "client-1"));
        clients.add(new Client("id-2", "client-2"));
        res.put("data", clients);
        return res;
    }

    @Operation(
            method = "Get",
            summary = "Query all clients by query param with Bean.",
            description = "Some descriptions...",
            tags = {"client"}
    )
    @GetMapping(value = "/client/list/v2")
    public Map<String, Object> selectAllClientsV2(
            // 获取 GET 请求中的查询参数方式一，使用一个Bean
            QueryPage queryPage
    ){
        System.out.println(queryPage);
        Map<String, Object> res = new HashMap<>();
        res.put("page", queryPage.getPage());
        res.put("size", queryPage.getSize());
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("id-3", "client-3"));
        clients.add(new Client("id-4", "client-4"));
        res.put("data", clients);
        return res;
    }

    @Operation(
            method = "Post",
            summary = "Add a client and echo it.",
            description = "Some descriptions...",
            tags = {"client"},
            responses = {
                    @ApiResponse(
                            description = "Client Bean",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))
                    )
            }
    )
    @PostMapping(value = "/client/add")
    public Client addOneClient(
            // 获取 POST 请求体的内容
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Client Bean",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)),
                    required = true
            )
            @RequestBody Client client
    ){
        System.out.println("new client: " + client);
        return client;
    }

}
