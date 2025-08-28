package pers.zxt.springboot.swagger.domain;

import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;


/**
 * Swagger 用于描述实体类主要使用如下注解：
 * @Schema
 * @ArraySchema
 */
@Data
@AllArgsConstructor
@Schema(description = "用户信息实体", requiredProperties = {"name", "email"})
public class User {

    @Schema(description = "用户唯一标识符", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "用户名", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 50)
    private String name;

    @Schema(description = "用户邮箱", example = "zhangsan@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "用户角色列表")
    @ArraySchema(schema = @Schema(type = "string", allowableValues = {"ADMIN", "USER", "GUEST"}))
    private List<String> roles;
}