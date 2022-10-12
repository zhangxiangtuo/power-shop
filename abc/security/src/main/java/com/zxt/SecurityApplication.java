package com.zxt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
* @author zhangxiangtuo
* @date 2022/10/10 16:20
*/
/*
    开启SpringSecurity的方法级别权限认证
        作用是：登录之后，根据不同的权限来访问不同的控制器方法
 */
@MapperScan(basePackages = {"com.zxt.mapper"})
//方法级别的访问权限控制
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableDiscoveryClient
@SpringBootApplication
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class,args);
    }
}
