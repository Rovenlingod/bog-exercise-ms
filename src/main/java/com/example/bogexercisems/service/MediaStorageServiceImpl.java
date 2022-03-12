package com.example.bogexercisems.service;

import com.example.bogexercisems.exception.StorageException;
import com.example.bogexercisems.properties.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class MediaStorageServiceImpl implements MediaStorageService {

    @Value("${storage.location}")
    private String rootLocation;
    private MinioProperties minioProperties;
    private MinioClient minioClient;

    @Autowired
    public MediaStorageServiceImpl(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
        this.minioClient = MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Override
    public String storeFile(MultipartFile file) {
        UUID newFileNameUUID = UUID.randomUUID();
        String newFileName = newFileNameUUID + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        storeFileInLocalStorage(file, newFileName);
        try {
            uploadFileToMinio(newFileName);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | MinioException e) {
            throw new StorageException("Exception occurred while uploading file to Minio", e);
        }
        deleteFileFromLocalStorage(newFileName);
        return newFileName;
    }

    @Override
    public String getFileUrlByName(String fileName) {
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(minioProperties.getBucket())
                    .object(fileName)
                    .expiry(1, TimeUnit.HOURS)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                | InvalidKeyException | InvalidResponseException | IOException
                | NoSuchAlgorithmException | XmlParserException | ServerException e) {
            log.warn("Could not load file with fileName: " + fileName + " from Minio server");
        }
        return url;
    }

    private void storeFileInLocalStorage(MultipartFile file, String filename) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
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
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
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
