package com.zxt;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhangxiangtuo
 * @date 2022/10/8 15:33
 */
@MapperScan(basePackages = "com.zxt.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class NacosBApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosBApplication.class,args);
    }
}
