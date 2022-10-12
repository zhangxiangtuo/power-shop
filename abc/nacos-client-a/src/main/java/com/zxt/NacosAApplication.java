package com.zxt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhangxiangtuo
 * @date 2022/10/8 15:33
 */
//允许加载属性配置类
@EnableHystrix
@EnableConfigurationProperties
@EnableFeignClients
@EnableDiscoveryClient
//排除掉数据源的自动配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NacosAApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosAApplication.class,args);
    }
}
