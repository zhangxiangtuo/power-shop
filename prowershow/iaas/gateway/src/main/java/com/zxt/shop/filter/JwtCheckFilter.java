package com.zxt.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class JwtCheckFilter implements GlobalFilter, Ordered {

    /*
        Jwt校验
            1、如果是登录操作的请求，/oauth/token,放行，让他去访问auth-server来获取token
            2、其他的请求必须传递token过来，才允许进行访问，不再网关中对token进行校验，我们有专门校验token的微服务
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取请求路径
        ServerHttpRequest request = exchange.getRequest();

//        System.out.println(request.getPath().toString());
//        System.out.println(request.getURI().getPath());
//        System.out.println(request.getLocalAddress().getAddress());

        String path = request.getURI().getPath();


        if(StringUtils.equals(path,"/aouth/token")){

        }

        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
