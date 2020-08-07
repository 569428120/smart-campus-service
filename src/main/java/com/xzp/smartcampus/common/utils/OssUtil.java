package com.xzp.smartcampus.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.xzp.smartcampus.config.AliyunOssConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author xuzhipeng
 */
@Component
public class OssUtil {

    @Resource
    private AliyunOssConfig ossConfig;

    /**
     * 删除文件
     *
     * @param keys keys
     */
    public void deleteByKeys(List<String> keys) {
        OSS client = null;
        try {
            client = this.openOssClient();
            client.deleteObjects(new DeleteObjectsRequest(ossConfig.getBucketname()).withKeys(keys));
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
    }

    /**
     * 打开客户端连接
     *
     * @return OSSClient
     */
    private OSS openOssClient() {
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getKeyid(), ossConfig.getKeysecret());
        String bucketName = ossConfig.getBucketname();
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(createBucketRequest);
        }
        return ossClient;
    }

    /**
     * 删除文件
     *
     * @param inputStream 输入流
     * @param fileId      文件id 用于生成路径
     * @return String
     */
    public String uploadFile(InputStream inputStream, String fileId, String fileName) {
        OSS client = null;
        String fileUrl = ossConfig.getFilehost() + "/" + (fileId + "_" + fileName);
        try {
            client = this.openOssClient();
            PutObjectResult result = client.putObject(new PutObjectRequest(ossConfig.getBucketname(), fileUrl, inputStream));
            System.out.println("");
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
        return fileUrl;
    }

}
