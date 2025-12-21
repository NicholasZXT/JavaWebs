package pers.zxt.cloud.commons.exception;

import lombok.Getter;
import pers.zxt.cloud.commons.enums.ErrorCode;

@Getter
public class BaseException extends RuntimeException {
    private final int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

}
