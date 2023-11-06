package com.example.amazons3.service.impl;

import com.example.amazons3.dto.File;
import com.example.amazons3.entity.Image;
import com.example.amazons3.repository.ImageRepository;
import com.example.amazons3.service.AmazonService;
import com.example.amazons3.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {
  @Autowired
  ImageRepository repository;

  @Autowired
  AmazonService amazonService;

  @Override
  public Image upload(MultipartFile file) {
    Image image = convertToImage(amazonService.uploadFile(file));
    repository.save(image);
    return image;
  }

  @Override
  public Image update(Long id, MultipartFile file) {
    Image image = repository.findById(id).orElse(null);
    if(Objects.nonNull(image)){
      image = updateToImage(image, amazonService.uploadFile(file));
      repository.save(image);
    }
    return image;
  }

  private Image convertToImage(File file) {
    return Image.builder()
          .name(file.getName())
          .bucketName(file.getBucketName())
          .url(file.getUrl())
          .contentType(file.getContentType())
          .build();
  }

  private Image updateToImage(Image image, File file) {
    image.setName(file.getName());
    image.setBucketName(file.getBucketName());
    image.setUrl(file.getUrl());
    image.setContentType(file.getContentType());
    return image;
  }
}
