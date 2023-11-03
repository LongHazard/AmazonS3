package com.example.amazons3.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.amazons3.dto.File;
import com.example.amazons3.service.AmazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AmazonServiceImpl implements AmazonService {

  @Value("${aws.s3.bucketName}")
  private String bucketName;

  @Autowired
  private AmazonS3 s3Client;
  @Override
  public File uploadFile(MultipartFile file) {
    try {
      String fileName = file.getOriginalFilename();
      String contentType = file.getContentType();
      s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), new ObjectMetadata()));
      String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
      return File.of(fileName, bucketName, contentType, fileUrl);
    } catch (IOException e) {
      throw new RuntimeException("Lỗi khi tải lên S3", e);
    }
  }
}
