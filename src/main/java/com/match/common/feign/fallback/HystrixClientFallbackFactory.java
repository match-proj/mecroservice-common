package com.match.common.feign.fallback;

import com.match.common.exception.BusinessException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author zhangchao
 * @Date 2019/8/1 17:28
 * @Version v1.0
 */
@Slf4j
public class HystrixClientFallbackFactory implements FallbackFactory {
    @Override
    public Object create(Throwable throwable) {
        log.error("系统调用链出现了错误：{}",throwable.getMessage());
        throw new BusinessException("系统调用链出现了错误");
    }
}
