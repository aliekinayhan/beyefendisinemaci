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

    @PostMapping("")
    public ResponseEntity<String> uploadFile (
            @RequestParam String fileName,
            @RequestParam MultipartFile file,
            @RequestParam String contentType) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.uploadFile(fileName,file.getBytes(),contentType));
    }


}
