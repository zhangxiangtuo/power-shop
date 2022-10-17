package com.zxt.shop.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxt.shop.constants.GatwayConstants;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/*
    拦截所有的请求操作
        只有登录操作放行，其他请求必须携带token
 */
@Configuration
public class JwtCheckFilter implements GlobalFilter, Ordered {
    /*
        Jwt校验
            1、如果是登录操作的请求，/oauth/token,放行，让他去访问auth-server来获取token
            2、其他的请求必须传递token过来，才允许进行访问，不再网关中对token进行校验，我们有专门校验token的微服务
     */
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取请求路径
        ServerHttpRequest request = exchange.getRequest();

//        System.out.println(request.getPath().toString());
//        System.out.println(request.getURI().getPath());
//        System.out.println(request.getLocalAddress().getAddress());

        String path = request.getURI().getPath();

        //如果是登录请求操作我们直接放行
        if(GatwayConstants.WHITE_LIST.contains(path)){
            //放行
            return  chain.filter(exchange);
        }
        //不是登录请求，必须携带token信息(请求头中 Authorization = bearer jwtToken...)
        //获取token的请求头信息
        String authorization = request.getHeaders().getFirst(GatwayConstants.TOKEN_AUTHORIZATION_PREFIX);

        if(StringUtils.isNotBlank(authorization)){

            //获取token中的jwt
            String token = authorization.replaceAll(GatwayConstants.TOKEN_BEARER_SUFFIX, "");

            //当前请求携带了token
            if (StringUtils.isNotBlank(token))
                //放行
                return  chain.filter(exchange);
        }

        //封装返回的jason数据
        HashMap<String, Object> resultMap = new HashMap<>();

        resultMap.put("code",20001);
        resultMap.put("msg","没有权限访问");
        resultMap.put("success",false);

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type","application/jason;charset=UTF-8");
        //输出来获取我们想要的信息
        //没有权限访问
        return response.writeWith(
            Mono.just(
                    response.bufferFactory().wrap(
                            new ObjectMapper().writeValueAsBytes(resultMap)
                    )
            )
        );

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
