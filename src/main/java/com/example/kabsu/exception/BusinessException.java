package com.example.kabsu.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(final ErrorCode errorCode, final Object... args) {
        super(getFormatterMessage(errorCode, args));
        this.errorCode = errorCode;
        this.args = args;
    }

    public static String getFormatterMessage(final ErrorCode errorCode, final Object[] args) {
        if (args != null && args.length > 0)
            return String.format(errorCode.getDefaultMessage(), args);
        return errorCode.getDefaultMessage();
    }
}
