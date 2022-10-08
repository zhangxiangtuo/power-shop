package com.zxt.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 13:04
 */
@MapperScan(basePackages = {"com.zxt.springcloud.mapper"})
@EnableEurekaClient
@SpringBootApplication
public class EurekaClientBApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientBApplication.class,args);
    }
}
