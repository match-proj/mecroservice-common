package com.match.common.icon;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author zhangchao
 * @Date 2019/6/21 9:27
 * @Version v1.0
 */
public interface IconServiceProvider<T> {

    void generate(T t, OutputStream outputStream) throws IOException;

}
