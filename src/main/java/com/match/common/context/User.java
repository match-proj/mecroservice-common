package com.match.common.context;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author zhangchao
 * @Date 2019/8/1 14:54
 * @Version v1.0
 */
@Getter
@Setter
public class User {
    private String id;
    private String phone;
    private String username;
    private String encodedPrincipal;
}
