package com.project.nextgen.minio;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;


//This service implements multi-tenant file storage by dynamically creating a bucket per customer 
//and storing files using MinIO. It also generates pre-signed URLs for secure file access.

@Service
@RequiredArgsConstructor
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    // ================================
    // 🔹 1. Generate Bucket per Customer
    // ================================
    private String getBucketName(String tenantId) {
        return "tenant-" + tenantId;
    }

    // ================================
    // 🔹 2. Create Bucket If Not Exists
    // ================================
    private void createBucketIfNotExists(String bucketName) throws Exception {
        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );

        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
            );
        }
    }

    // ================================
    // 🔹 3. Upload File (MULTI-TENANT)
    // ================================
    public void uploadFile(String tenantId,
                           String objectName,
                           InputStream stream,
                           long size,
                           String contentType) throws Exception {

        String bucketName = getBucketName(tenantId);
        System.out.println("Creating/checking bucket: " + bucketName);
        // Ensure bucket exists
        createBucketIfNotExists(bucketName);

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(stream, size, -1)
                        .contentType(contentType)
                        .build()
        );
    }

    // ================================
    // 🔹 4. Get Download URL
    // ================================
    public String getDownloadUrl(String tenantId, String objectName) throws Exception {

        String bucketName = getBucketName(tenantId);
        System.out.println("getDownloadUrl bucketName: " + bucketName);

        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(1, TimeUnit.HOURS)
                        .build()
        );
    }

    public InputStream getFile(String objectName) throws Exception {
        return minioClient.getObject(
            GetObjectArgs.builder()
               .bucket("tenant-107") //currently hardcoded
                .object(objectName)
                .build()
        );
    }
}







//package com.project.nextgen.minio;
//
//import java.io.InputStream;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import io.minio.GetPresignedObjectUrlArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import io.minio.http.Method;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class MinioService {
//
//	@Autowired
//    private MinioClient minioClient;
//
//    @Value("${minio.bucket}")
//    private String bucket;
//
//    public void uploadFile(String objectName, InputStream stream, long size, String type) throws Exception {
//
//        minioClient.putObject(
//                PutObjectArgs.builder()
//                        .bucket(bucket)
//                        .object(objectName)
//                        .stream(stream, size, -1)
//                        .contentType(type)
//                        .build()
//        );
//    }
//    
//    public String getDownloadUrl(String objectName) throws Exception {
//        return minioClient.getPresignedObjectUrl(
//                GetPresignedObjectUrlArgs.builder()
//                        .method(Method.GET)
//                        .bucket(bucket)
//                        .object(objectName)
//                        .expiry(1, TimeUnit.HOURS)
//                        .build()
//        );
//    }
//}
