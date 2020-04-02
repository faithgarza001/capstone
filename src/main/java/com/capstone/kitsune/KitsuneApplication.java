package com.capstone.kitsune;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class KitsuneApplication {

    public static void main(String[] args) {
        SpringApplication.run(KitsuneApplication.class, args);
    }


    //here your going to create your bean for the rest template for the api
//    @Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }
}
