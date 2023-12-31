package com.hl.hhuserinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HhUserInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(HhUserInfoApplication.class, args);
    }
}
