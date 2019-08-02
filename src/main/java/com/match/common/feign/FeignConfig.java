package com.match.common.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @Author zhangchao
 * @Date 2019/8/2 11:29
 * @Version v1.0
 */
public class FeignConfig {
    @Bean
    Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }
}
