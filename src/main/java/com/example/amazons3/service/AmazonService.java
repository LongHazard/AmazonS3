package com.example.amazons3.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonService {
  String uploadFile(MultipartFile file);
}
