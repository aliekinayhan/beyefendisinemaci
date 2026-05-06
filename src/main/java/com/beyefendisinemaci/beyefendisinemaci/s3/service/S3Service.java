package com.beyefendisinemaci.beyefendisinemaci.s3.service;

import com.beyefendisinemaci.beyefendisinemaci.s3.exception.EmptyFileException;
import com.beyefendisinemaci.beyefendisinemaci.s3.exception.FileSizeExceededException;
import com.beyefendisinemaci.beyefendisinemaci.s3.exception.InvalidFileExtensionException;
import com.beyefendisinemaci.beyefendisinemaci.s3.exception.InvalidFileTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Value("${aws.cloudfront-url}")
    private String cloudFrontUrl;

    private final S3Client s3Client;

    public String uploadFile(MultipartFile file, String prefix) throws IOException {
        validateFile(file, prefix);
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        String contentType = file.getContentType();

        String fileName = prefix + "/" + UUID.randomUUID() + extension;
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();
        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        return cloudFrontUrl + "/" + fileName;
    }

    private void validateFile(MultipartFile file, String prefix) {
        if (file.isEmpty()) throw new EmptyFileException();

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) throw new InvalidFileTypeException();

        String contentType = file.getContentType();
        if (contentType == null) throw new InvalidFileTypeException();

        List<String> allowedImageExtensions = List.of(".jpg", ".jpeg", ".png", ".webp");
        List<String> allowedVideoExtensions = List.of(".mp4", ".mov", ".avi");

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (prefix.contains("photos") && !allowedImageExtensions.contains(extension))
            throw new InvalidFileExtensionException();
        if (prefix.contains("videos") && !allowedVideoExtensions.contains(extension))
            throw new InvalidFileExtensionException();

        if (prefix.contains("photos") && !contentType.startsWith("image/"))
            throw new InvalidFileTypeException();
        if (prefix.contains("videos") && !contentType.startsWith("video/"))
            throw new InvalidFileTypeException();

        if (prefix.contains("photos") && file.getSize() > 5 * 1024 * 1024)
            throw new FileSizeExceededException("Photo can not be larger than 5MB");
        if (prefix.contains("videos") && file.getSize() > 500 * 1024 * 1024)
            throw new FileSizeExceededException("Video can not be larger 500MB");

    }
}

