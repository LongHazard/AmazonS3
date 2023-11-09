package com.example.amazons3.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.amazons3.dto.File;
import com.example.amazons3.service.AmazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

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
      String fileUrl = generatePresignedUrl(fileName);
      return File.of(fileName, bucketName, contentType, fileUrl);
    } catch (IOException e) {
      throw new RuntimeException("Lỗi khi tải lên S3", e);
    }
  }

  // Tạo URL có thời hạn truy cập file từ S3 mà không cần phải xác thực AWS
  private String generatePresignedUrl(String fileName) {
    try {
      // Xác định thời gian hiệu lực của URL (ví dụ: 1 giờ)
      java.util.Date expiration = new java.util.Date();
      long expTimeMillis = expiration.getTime();
      expTimeMillis += 60 * 60 * 1000; // 1 giờ
      expiration.setTime(expTimeMillis);

      // Tạo yêu cầu ký hiệu URL
      GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
            .withMethod(HttpMethod.GET) // Hoặc HttpMethod.PUT để tải lên tùy vào mục đích sử dụng
            .withExpiration(expiration);

      // Tạo URL đã ký hiệu
      URL presignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

      return presignedUrl.toString();
    } catch (AmazonServiceException e) {
      throw new RuntimeException("Lỗi khi tạo URL ký hiệu", e);
    }
  }
}
