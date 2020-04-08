package com.capstone.kitsune;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan({"com.capstone.kitsune", "controllers"})
public class KitsuneApplication {

    public static void main(String[] args) {
        new File(FileUploadController.uploadDirectory).mkdir();
        SpringApplication.run(KitsuneApplication.class, args);
    }


    //here your going to create your bean for the rest template for the api
//    @Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }
}
