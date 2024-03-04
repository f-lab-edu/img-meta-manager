package com.intelligent.imagetagmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ImageTagManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageTagManagementApplication.class, args);
    }

}
