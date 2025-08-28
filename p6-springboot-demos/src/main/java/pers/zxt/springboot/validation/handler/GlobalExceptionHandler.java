package pers.zxt.springboot.validation.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 在校验过程中，javax.validation (Bean Validation) 和 Hibernate Validator 在校验失败时会抛出特定的异常，
 * 而 Spring 框架会捕获这些异常并将其转换为更适合 Web 层处理的异常。
 *
 * Bean Validation / Hibernate Validator 抛出的原始异常一般是 javax.validation.ConstraintViolationException :
 *   - 这是 Bean Validation 规范定义的核心异常。
 *   - 当使用 Validator 接口直接对对象进行校验（例如通过 validator.validate(object)）时，如果校验失败，就会抛出此异常。
 *   - 它包含一组 ConstraintViolation 对象，每个对象代表一个校验失败的详细信息（如属性路径、错误消息、无效值等）。
 *
 * Spring框架会捕获上述异常，并根据不同的场景将其转换为更具体的、更适合处理的异常，主要是如下 3 类：
 * (1) MethodArgumentNotValidException
 *   - 当使用 @Valid 或 @Validated 校验 @RequestBody 的控制器方法参数（通常是 DTO/VO）失败时抛出。
 *   - 可以通过 getBindingResult() 方法获取 BindingResult，进而获取所有校验错误的详细信息（字段名、错误消息、拒绝原因等）。
 * (2) BindException
 *   - 当校验 表单数据 (@ModelAttribute) 或 请求参数 (@RequestParam, @PathVariable) 失败时抛出
 *   - 同样包含 BindingResult，可以从中获取详细的错误信息。
 * (3) ConstraintViolationException
 *   - 虽然在典型的 Spring MVC 校验场景中较少直接遇到，但在某些情况下（如使用 @Validated 进行方法级别校验但没有通过 @RequestBody 传参），
 *     或者在非 Web 层（如 Service 层）直接使用 Validator 进行校验时，仍可能抛出这个原始异常。
 *   - Spring 也提供了对应的异常处理器 @ExceptionHandler(ConstraintViolationException.class) 来处理这种异常。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 @RequestBody 校验失败 (MethodArgumentNotValidException)
     * 例如: POST /users 时，UserDTO 字段校验不通过。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        // 收集所有字段校验错误
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            // 可以选择只保留第一个错误，或收集所有
            // 如果同一个字段有多个错误，这里会覆盖，保留最后一个
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        // 或者使用 Java 8 Stream 收集 (更简洁)
        // Map<String, String> errors = bindingResult.getFieldErrors().stream()
        //         .collect(Collectors.toMap(
        //                 FieldError::getField,
        //                 FieldError::getDefaultMessage,
        //                 (existing, replacement) -> existing // 处理键冲突，保留第一个
        //         ));

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                "请求数据校验失败",
                errors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    //@ExceptionHandler(MethodArgumentNotValidException.class)
    //public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidExceptionV2(MethodArgumentNotValidException ex) {
    //    BindingResult bindingResult = ex.getBindingResult();
    //    Map<String, String> errors = new HashMap<>();
    //    for (FieldError fieldError : bindingResult.getFieldErrors()) {
    //        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    //    }
    //    return ResponseEntity.badRequest().body(errors);
    //}

    /**
     * 处理 @ModelAttribute 或表单参数校验失败 (BindException)
     * 例如: 表单提交时字段校验不通过。
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));
        ErrorResponse errorResponse = new ErrorResponse(
                "BINDING_ERROR",
                "表单数据绑定或校验失败",
                errors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    //@ExceptionHandler(BindException.class)
    //public ResponseEntity<Map<String, String>> handleBindExceptionV2(BindException ex) {
    //    BindingResult bindingResult = ex.getBindingResult();
    //    Map<String, String> errors = new HashMap<>();
    //    for (FieldError fieldError : bindingResult.getFieldErrors()) {
    //        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    //    }
    //    // 如果没有字段错误，则处理全局错误
    //    if (errors.isEmpty()) {
    //        errors.put("error", ex.getMessage());
    //    }
    //    return ResponseEntity.badRequest().body(errors);
    //}

    /**
     * 处理方法参数/返回值校验失败 (ConstraintViolationException)
     * 例如: Service 层方法参数使用 @Min, @NotBlank 等注解校验失败。
     * 或 JPA 实体在持久化时校验失败。
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        // 收集所有违反的约束
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        // 获取违反约束的属性路径 (如 "user.address.street")
                        violation -> violation.getPropertyPath().toString(),
                        // 获取错误消息
                        violation -> violation.getMessage(),
                        // 处理路径冲突 (理论上路径唯一，但安全起见)
                        (existing, replacement) -> existing + "; " + replacement
                ));
        ErrorResponse errorResponse = new ErrorResponse(
                "CONSTRAINT_VIOLATION",
                "参数或数据约束违反",
                errors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 【可选】处理其他未预期的异常，避免暴露敏感信息给客户端。
     * 生产环境中，可以记录详细日志，返回通用错误。
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        // 建议使用日志框架 (如 SLF4J) 记录详细错误堆栈
        // log.error("Unexpected error occurred", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_ERROR",
                "服务器内部错误"
                // 生产环境不要返回 ex.getMessage()，避免信息泄露
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // --- 响应数据模型 ---
    /**
     * 统一的错误响应体
     */
    @Data
    public static class ErrorResponse {
        // Getters and Setters (Lombok @Data 可替代)
        private String code;           // 错误码
        private String message;        // 错误描述
        private Map<String, String> errors; // 字段级错误详情 (可选)

        // 构造函数 (errors 可为 null)
        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public ErrorResponse(String code, String message, Map<String, String> errors) {
            this.code = code;
            this.message = message;
            this.errors = errors;
        }

    }

}
