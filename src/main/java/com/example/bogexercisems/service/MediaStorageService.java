package com.example.bogexercisems.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MediaStorageService {
    UUID storeFile(MultipartFile file);
}
