package com.example.bogexercisems.service;

import com.example.bogexercisems.exception.StorageException;
import com.example.bogexercisems.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Log4j2
@Service
public class MediaStorageServiceImpl implements MediaStorageService {

    @Value("${storage.location}")
    private String rootLocation;
    private MinioProperties minioProperties;

    @Autowired
    public MediaStorageServiceImpl(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Override
    public UUID storeFile(MultipartFile file) {
        UUID newFileName = UUID.randomUUID();
        storeFileInLocalStorage(file, newFileName.toString());
        try {
            uploadFileToMinio(newFileName.toString());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | MinioException e) {
            throw new StorageException("Exception occurred while uploading file to Minio", e);
        }
        deleteFileFromLocalStorage(newFileName.toString());
        return newFileName;
    }

    private void storeFileInLocalStorage(MultipartFile file, String filename) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + originalFileName);
            }
            if (filename.contains("..")) {
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + originalFileName);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream,
                        Paths.get(rootLocation).resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFileName, e);
        }
    }

    private void uploadFileToMinio(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(minioProperties.getEndpoint())
                        .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                        .build();
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
        }
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .object(fileName)
                        .filename(Paths.get(rootLocation).resolve(fileName).toString())
                        .build()
        );
    }

    private void deleteFileFromLocalStorage(String fileName) {
        File file = new File(Paths.get(rootLocation).resolve(fileName).toString());
        if (file.delete()) {
            log.info("Deleted file from local storage with fileName: {}", fileName);
        } else {
            log.warn("Failed to delete file from local storage with fileName: {}", fileName);
        }
    }
}
