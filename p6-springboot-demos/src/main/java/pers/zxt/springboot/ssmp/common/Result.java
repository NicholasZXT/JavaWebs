package pers.zxt.springboot.ssmp.common;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 在 Java Spring Boot 项目中，有两种通用返回体设计：
 * (1) Result<T>，强类型封装，基于泛型类实现
 *   - 类型安全，使用泛型 <T>，编译期就能知道 data 字段的具体类型。
 *   - 序列化/反序列化兼容性好
 *   - 语义清晰、可读性强，字段命名明确（code, message, data），符合 Java Bean 规范，IDE可以自动提示
 *   - 易于扩展，可以添加方法如 isOk()、hasData() 等业务逻辑判断。
 *   - 灵活性略低，不适合“高度动态”的返回结构
 * (2) AjaxResult，动态结构，基于 Map 实现
 *   - 高度灵活、动态扩展，可随时通过 put("xxx", value) 添加任意字段；适合需要“非标准”返回结构的场景。
 *   - 轻量、无泛型约束
 *   - 缺乏类型安全，data 字段是 Object，调用方无法知道具体类型，容易出错；IDE无法自动提示
 *   - 与 OpenAPI/Swagger 集成困难
 * 总结：
 *   对于绝大多数前后端分离的 Spring Boot 项目，尤其是中大型、需要长期维护、有 API 文档要求的系统，
 *   强类型的 Result<T> 是更专业、更安全、更可维护的选择。
 * @param <T>
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL) // 避免 data=null 出现在 JSON 中
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;

    // 私有构造，强制通过静态工厂方法创建
    private Result() {}

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ====== 成功 ======
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    // ====== 失败（通用）======
    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> fail(String message) {
        return fail(ResultCode.FAIL.getCode(), message);
    }

    // ====== 常用 HTTP 语义错误 ======
    public static <T> Result<T> badRequest(String message) {
        return fail(ResultCode.VALIDATION_ERROR.getCode(), message);
    }

    public static <T> Result<T> unauthorized(String message) {
        return fail(ResultCode.UNAUTHORIZED.getCode(), message);
    }

    public static <T> Result<T> forbidden(String message) {
        return fail(ResultCode.FORBIDDEN.getCode(), message);
    }

    public static <T> Result<T> notFound(String message) {
        return fail(ResultCode.NOT_FOUND.getCode(), message);
    }

    // ====== 自定义数据 + 状态码（高级用法）======
    public static <T> Result<T> of(ResultCode resultCode, T data) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <T> Result<T> of(int code, String message, T data) {
        return new Result<>(code, message, data);
    }
}