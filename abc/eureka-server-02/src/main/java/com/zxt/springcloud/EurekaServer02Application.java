package com.zxt.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 10:34
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer02Application {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer02Application.class,args);
    }
}
