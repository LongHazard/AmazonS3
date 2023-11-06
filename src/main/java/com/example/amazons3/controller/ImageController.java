package com.example.amazons3.controller;

import com.example.amazons3.entity.Image;
import com.example.amazons3.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/s3/image")
public class ImageController {
  @Autowired
  ImageService imageService;

  @PostMapping("/upload")
  public ResponseEntity<Image> uploadToS3(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok().body(imageService.upload(file));
  }

  @PutMapping("{id}")
  public ResponseEntity<Image> uploadToS3(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok().body(imageService.update(id, file));
  }
}
