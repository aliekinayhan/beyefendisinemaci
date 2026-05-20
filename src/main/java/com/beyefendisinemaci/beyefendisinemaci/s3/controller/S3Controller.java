package com.beyefendisinemaci.beyefendisinemaci.s3.controller;


import com.beyefendisinemaci.beyefendisinemaci.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/s3/upload")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service service;

    @PostMapping("/profile-photo")
    public ResponseEntity<String> uploadProfilePhoto(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.uploadFile(file, "profile-photos"));
    }

    @PostMapping("/cover-photo")
    public ResponseEntity<String> uploadCoverPhoto(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.uploadFile(file, "cover-photos"));
    }

    @PostMapping("/video-long")
    public ResponseEntity<String> uploadLongVideo(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.uploadFile(file, "videos/long"));
    }

    @PostMapping("/video-short")
    public ResponseEntity<String> uploadShortVideo(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.uploadFile(file, "videos/short"));
    }

    @GetMapping("/presigned-url/profile-photo")
    public ResponseEntity<Map<String, String>> getProfilePhotoPresignedUrl(@RequestParam String extension) {
        return ResponseEntity.ok(service.generatePresignedUrl("profile-photos", extension));
    }

    @GetMapping("/presigned-url/cover-photo")
    public ResponseEntity<Map<String, String>> getCoverPhotoPresignedUrl(@RequestParam String extension) {
        return ResponseEntity.ok(service.generatePresignedUrl("cover-photos", extension));
    }

    @GetMapping("/presigned-url/video-long")
    public ResponseEntity<Map<String, String>> getLongVideoPresignedUrl(@RequestParam String extension) {
        return ResponseEntity.ok(service.generatePresignedUrl("videos/long", extension));
    }

    @GetMapping("/presigned-url/video-short")
    public ResponseEntity<Map<String, String>> getShortVideoPresignedUrl(@RequestParam String extension) {
        return ResponseEntity.ok(service.generatePresignedUrl("videos/short", extension));
    }


}
