package com.example.amazons3.service;

import com.example.amazons3.dto.File;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonService {
  File uploadFile(MultipartFile file);
}
