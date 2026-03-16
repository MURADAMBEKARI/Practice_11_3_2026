package com.project.nextgen.minio;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MinioService {

	@Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public void uploadFile(String objectName, InputStream stream, long size, String type) throws Exception {

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .stream(stream, size, -1)
                        .contentType(type)
                        .build()
        );
    }
}
