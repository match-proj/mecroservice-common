package com.match.common.file;

import java.io.File;
import java.io.InputStream;

/**
 * @Author zhangchao
 * @Date 2019/5/23 17:03
 * @Version v1.0
 */
public interface ObjectUpload {
    String upload(File file);
    String upload(File file, String name);
    String upload(InputStream inputStream, String name);
}
