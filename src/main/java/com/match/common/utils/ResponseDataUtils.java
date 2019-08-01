package com.match.common.utils;


import com.match.common.ResponseData;
import com.match.common.exception.BusinessException;

/**
 * @Author zhangchao
 * @Date 2019/4/25 17:38
 * @Version v1.0
 */
public class ResponseDataUtils {

    private final static String SUCCESS  = "OK";


    public static ResponseData buildSuccess() {
        return new ResponseData(200,SUCCESS);
    }

    public static ResponseData buildSuccess(Object obj) {
        return new ResponseData(200, SUCCESS).addObject(obj);
    }

    public static ResponseData buildSuccess(String code, String msg) {
        return new ResponseData(200, msg);
    }

    public static <T> ResponseData buildSuccess(String code, String msg, Object data) {
        return new ResponseData<T>(200, msg).addObject(data);
    }

    public static ResponseData buildError(String msg) {
        return new ResponseData(400, msg);
    }

    public static ResponseData buildError(BusinessException msg) {
        return new ResponseData(400, msg);
    }

    public static <T> ResponseData buildError(String code, String msg) {
        return new ResponseData<T>(Integer.valueOf(code), msg);
    }
}
