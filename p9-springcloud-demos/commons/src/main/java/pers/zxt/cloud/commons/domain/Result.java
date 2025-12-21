package pers.zxt.cloud.commons.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonInclude;
import pers.zxt.cloud.commons.enums.ResultCode;


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