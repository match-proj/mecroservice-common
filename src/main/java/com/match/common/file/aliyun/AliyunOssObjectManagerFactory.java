package com.match.common.file.aliyun;

import com.match.common.file.ObjectManagerFactory;
import com.match.common.file.ObjectUpload;

import java.io.File;
import java.io.InputStream;

/**
 * @Author zhangchao
 * @Date 2019/5/23 17:04
 * @Version v1.0
 */
public class AliyunOssObjectManagerFactory implements ObjectManagerFactory, ObjectUpload {

    private AliyunOssObjectUpload aliyunOssFileUpload;

    private String endpoint = "http://oss-cn-shanghai.aliyuncs.com";

    private String accessKeyId = "LTAIFsVHjr9VrjJf";

    private String accessKeySecret = "YNSl2YEePFGwZd6vNZvwG9Z8CeLFZ5";

    private String bucketName = "flyants";


    public AliyunOssObjectManagerFactory() {
        aliyunOssFileUpload = new AliyunOssObjectUpload(endpoint,accessKeyId,accessKeySecret,bucketName);
    }

    @Override
    public String upload(File file) {
        return aliyunOssFileUpload.upload(file);
    }

    @Override
    public String upload(InputStream inputStream, String name) {
        return aliyunOssFileUpload.upload(inputStream,name);
    }

    @Override
    public String upload(File file, String name) {
        return aliyunOssFileUpload.upload(file,name);
    }


//    public static void main(String[] args) throws Exception {
//        AliyunOssObjectManagerFactory build = ObjectManagerFactory.build();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ImageUtil.generateImg("Juck",byteArrayOutputStream);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//        String upload = build.upload(inputStream, "headimg/juck.jpg");
//        System.out.println(upload);
//    }

}
