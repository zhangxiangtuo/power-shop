package com.zxt;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan(basePackages = {"com.zxt.mapper"})
@EnableDiscoveryClient
@SpringBootApplication
public class Auth2Application {
    public static void main(String[] args) {
        SpringApplication.run(Auth2Application.class,args);
    }
}
