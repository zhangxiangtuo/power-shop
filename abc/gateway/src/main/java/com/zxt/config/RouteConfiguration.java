package com.zxt.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.*;

/**
 * @author zhangxiangtuo
 * @date 2022/10/9 19:15
 */

/**
 * 通过代码方式生成路由规则，代替了在aplication.yam文件里路由规则了
 */
@Configuration
public class RouteConfiguration {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {

        return builder
                //路由规则配置
                .routes()
                //将整个路径还有服务名称装到r里面，传到bulider对象里，在传到RouteLocator路由对象
                //.route("toA",r -> r.path("/a/activity/**").uri("lb://nacos-client-a"))
                //如果是想要配置多个路由规则的话就,此时的断言规则是bbb的
                .route("toB", r -> {
                    /*********** 断言规则**************/
                    return r.path("/b/activity/**")
                            //通过and的方式连接多个断言规则
                            .and()
                            .between(
                                    //允许访问的时间间隔，之前加负的之后加正的
                                    ZonedDateTime.now().plusHours(-1),
                                    ZonedDateTime.now().plusHours(1)
                            )
                            //.and()
                            //.cookie("ccc","ddd")
                            /*********** 过滤器规则**************/
                            .filters(f -> {
                                return f.addRequestHeader("aaa","bbb")
                                        .addRequestParameter("bbb","ccc")
                                        .addResponseHeader("ccc","ddd")
                                        .setStatus(404)
                                        //.modifyResponseBody(String.class,String.class,((exchange, s) -> {
                                        //    System.out.println("响应体中的真实json数据："+s);
                                        //    //修改后的返回体中的数据
                                        //    return Mono.just("网络异常，请稍后再试...");
                                        //}))
                                        ;
                            })
                            .uri("lb://nacos-client-b");
                })
                .build();

    }
}
