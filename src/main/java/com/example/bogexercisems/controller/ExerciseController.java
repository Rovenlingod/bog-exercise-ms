package com.example.bogexercisems.controller;

import com.example.bogexercisems.service.MediaStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ExerciseController {
    @Autowired
    private MediaStorageService storageService;

    @PostMapping("/test")
    public ResponseEntity<String> storeFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(storageService.storeFile(file).toString());
    }

    @GetMapping("/testget/{id}")
    public ResponseEntity<String> getFile(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(storageService.getFileUrlByName(id));
    }
}
