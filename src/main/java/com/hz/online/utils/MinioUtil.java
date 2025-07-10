package com.hz.online.utils;

import io.minio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class MinioUtil {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    private MinioClient getClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    // 上传文件
    public String upload(MultipartFile file) throws Exception {
        MinioClient client = getClient();

        // 确保 bucket 存在
        boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            client.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        }

        return endpoint + "/" + bucketName + "/" + filename;
    }

    public String upload(File file, String contentType) throws Exception {
        MinioClient client = getClient();

        boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        String datePath = LocalDate.now().toString(); // 如：2025-07-08
        String filename = datePath + "/" + file.getName();
//        String filename = UUID.randomUUID().toString() + "-" + file.getName();
        try (InputStream inputStream = new FileInputStream(file)) {
            client.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .stream(inputStream, file.length(), -1)
                    .contentType(contentType)
                    .build());
        }

        return endpoint + "/" + bucketName +  "/" + filename;
    }


    // 下载文件
    public InputStream download(String objectName) throws Exception {
        MinioClient client = getClient();
        return client.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    // 删除文件
    public void delete(String objectName) throws Exception {
        MinioClient client = getClient();
        client.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    public String getObjectNameFromUrl(String url) {
        String prefix = endpoint + "/" + bucketName + "/";
        if (url.startsWith(prefix)) {
            return url.substring(prefix.length());
        } else {
            throw new IllegalArgumentException("无效的文件地址");
        }
    }

}
