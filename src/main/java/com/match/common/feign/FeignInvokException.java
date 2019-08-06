package com.match.common.feign;

import feign.FeignException;

/**
 * @Author zhangchao
 * @Date 2019/8/6 10:30
 * @Version v1.0
 */
public class FeignInvokException extends FeignException {
    protected FeignInvokException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeignInvokException(String message) {
        super(message);
    }
}
