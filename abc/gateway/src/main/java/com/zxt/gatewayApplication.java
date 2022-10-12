package com.zxt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.ZonedDateTime;

/**
 * @author zhangxiangtuo
 * @date 2022/10/9 17:04
 */
@EnableDiscoveryClient
@SpringBootApplication
public class gatewayApplication {
    public static void main(String[] args) {

        System.out.println(ZonedDateTime.now().toString());
        SpringApplication.run(gatewayApplication.class,args);
    }
}
