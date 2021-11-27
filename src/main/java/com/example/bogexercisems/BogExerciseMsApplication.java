package com.example.bogexercisems;

import com.example.bogexercisems.properties.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MinioProperties.class)
public class BogExerciseMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BogExerciseMsApplication.class, args);
    }

}
