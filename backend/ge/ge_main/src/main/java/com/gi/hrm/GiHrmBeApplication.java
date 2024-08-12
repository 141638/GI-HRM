package com.gi.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GiHrmBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(GiHrmBeApplication.class, args);
    }
}
