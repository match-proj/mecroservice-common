package com.match.common.file.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.match.common.file.ObjectUpload;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author zhangchao
 * @Date 2019/5/23 17:07
 * @Version v1.0
 */
public class AliyunOssObjectUpload implements ObjectUpload {

    private String endpoint;
    private String accessKeyId  ;
    private String accessKeySecret  ;
    private String bucketName  ;


    public String buildUrl(String name){
        URI uri = null;
        try {
            uri = new URI(endpoint);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "http://"+bucketName+"."+uri.getHost()+"/"+name;
    }


    public AliyunOssObjectUpload(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    @Override
    public String upload(File file) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        ossClient.putObject(bucketName, file.getName(), file);
        ossClient.shutdown();
        return buildUrl(file.getName());
    }

    @Override
    public String upload(File file, String name) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        ossClient.putObject(bucketName,name, file);
        ossClient.shutdown();
        return buildUrl(name);
    }

    @Override
    public String upload(InputStream inputStream, String name) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        ossClient.putObject(bucketName,name, inputStream);
        ossClient.shutdown();
        return buildUrl(name);
    }
}
