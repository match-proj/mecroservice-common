package com.match.common.exception;

import com.match.common.ResponseData;
import com.match.common.utils.ResponseDataUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Author zhangchao
 * @Date 2019/4/25 16:32
 * @Version v1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(value = {TokenExpireException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseData cutTokenExpireException(TokenExpireException ex) {
        log.error("cutTokenExpireException");
        log.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseDataUtils.buildError(ex.getErrorCode(), ex.getErrorMsg());
    }

    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData cutBusinessException(BusinessException ex) {
        log.error("cutBusinessException");
        ex.printStackTrace();
        return ResponseDataUtils.buildError(ex);
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData cutConstraintViolationException(ConstraintViolationException ex) {
        log.error("cutConstraintViolationException");
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errorMsg = new LinkedList<>();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            for (ConstraintViolation<?> violation : constraintViolations) {
                errorMsg.add(violation.getMessage());
            }
        }
        log.error(errorMsg.toString());
        return ResponseDataUtils.buildError(errorMsg.toString()).setClazz(ex);
    }



    @ExceptionHandler(value = {FeignException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData cutFeignException(FeignException ex) {
        log.error("cutFeignException");
        log.error(ex.getMessage());
        return ResponseDataUtils.buildError(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", ex.getMessage()).setClazz(ex);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData cutException(Exception ex) {
        log.error("cutException");
        ex.printStackTrace();
        return ResponseDataUtils.buildError(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", ex.getMessage()).setClazz(ex);
    }
}
