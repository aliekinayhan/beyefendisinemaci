package com.beyefendisinemaci.beyefendisinemaci.s3.controller;


import com.beyefendisinemaci.beyefendisinemaci.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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



}
