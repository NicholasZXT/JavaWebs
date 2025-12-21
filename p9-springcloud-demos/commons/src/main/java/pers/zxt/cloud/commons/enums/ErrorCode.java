package pers.zxt.cloud.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数错误"),
    USER_NOT_FOUND(40401, "用户不存在"),
    PRODUCT_NOT_FOUND(40402, "商品不存在"),
    ORDER_CREATE_FAILED(50001, "订单创建失败");

    private final int code;
    private final String message;
}
