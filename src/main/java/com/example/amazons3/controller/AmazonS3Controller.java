package com.example.amazons3.controller;

import com.example.amazons3.service.AmazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/s3")
public class AmazonS3Controller {
  @Autowired
  private AmazonService amazonS3Service;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadToS3(@RequestParam("file") MultipartFile file) {
    String url = amazonS3Service.uploadFile(file);
    return ResponseEntity.ok(url);
  }
}
