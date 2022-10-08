package com.zxt.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 10:28
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer01Application {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer01Application.class,args);
    }
}
