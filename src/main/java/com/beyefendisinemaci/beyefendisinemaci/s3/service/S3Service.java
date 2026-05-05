package com.beyefendisinemaci.beyefendisinemaci.s3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    private final S3Client s3Client;

    public String uploadFile(String fileName, byte[] fileData, String contentType) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();
        s3Client.putObject(request, RequestBody.fromBytes(fileData));
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }

}

