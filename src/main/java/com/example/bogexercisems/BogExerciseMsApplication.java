package com.example.bogexercisems;

import com.example.bogexercisems.properties.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(MinioProperties.class)
@EnableFeignClients
@EnableAspectJAutoProxy
public class BogExerciseMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BogExerciseMsApplication.class, args);
    }

}
