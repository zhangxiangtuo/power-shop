package com.zxt.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 12:11
 */

//开启Feign远程调用
@EnableFeignClients
//开启注册发现功能
@EnableEurekaClient
//排除掉数据源的自动配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EurekaClientAApplication {
     /*
        Spring提供的发送http的restful方式的请求
            Get    查询
            Post   新增
            Put    修改
            Delete 删除
    */
    @Bean//将当前方法的返回值交给Spring容器进行管理，容器的id就是方法名称

    @LoadBalanced//负载均衡调用
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientAApplication.class,args);
    }
}
