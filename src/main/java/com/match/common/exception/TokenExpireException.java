package com.match.common.exception;

/**
 * @author: zhangchao
 * @time: 2018-08-21 09:46
 **/
public class TokenExpireException extends BusinessException {

    public TokenExpireException(String errorMsg) {
        super(errorMsg);
    }

    public TokenExpireException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public TokenExpireException(String message, String errorCode, String errorMsg) {
        super(message, errorCode, errorMsg);
    }

    public TokenExpireException(String message, Throwable cause, String errorCode, String errorMsg) {
        super(message, cause, errorCode, errorMsg);
    }

    public TokenExpireException(Throwable cause, String errorCode, String errorMsg) {
        super(cause, errorCode, errorMsg);
    }

    public TokenExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorMsg) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode, errorMsg);
    }
}
