package com.example.amazons3.service;

import com.example.amazons3.entity.Image;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {
  Image upload(MultipartFile file);

  Image update(Long id, MultipartFile file);
}
