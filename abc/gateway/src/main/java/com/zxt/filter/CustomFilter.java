package com.zxt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxiangtuo
 * @date 2022/10/10 8:41
 */
/*
     实现网关的自定义过滤器
        1、实现两个接口，并重写方法
            GlobalFilter重写filter方法
                具体的过滤器的实现方式
            Ordered重写getOrder方法
                过滤器的执行顺序，数字越小优先级越高
                int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
                int LOWEST_PRECEDENCE = Integer.MAX_VALUE;
 */
@Component
public class CustomFilter implements GlobalFilter, Ordered {

    /*
        具体的过滤器拦截操作
            我们可以在此处进行权限校验操作
            没学权限校验框架的时候可以这样操作，
            学过后要交给框架去校验
     */
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        /*
            当请求没有传递json参数时，我们将拦截，不再向下执行请求转发的操作
                token:令牌，代表我们的身份信息，通常是需要加密的，我们返回的是一串加密后的字符串
                    Jwt ：json web token,将token（身份信息），通过加密算法的方式，加密成密文的字符串
         */
        //通过exchange对象来获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst("token");

        //token没有传递，或者token传递的是一个空字符串信息
        if(StringUtils.isBlank(token)){
            //返回失败的结果
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("code",20001);
            resultMap.put("msg","当前手机信号异常请稍后重试");

            //设置响应json数据的编码，防止乱码
            exchange.getResponse().getHeaders().add("Content-Type","application/json;chartset=UTF-8");

            //获取响应的对象进行返回
            return  exchange.getResponse().writeWith(
                    //创建并返回一个对象
                    Mono.just(
                            exchange.getResponse().bufferFactory().wrap(
                                    //通过fastJson返回json数据
                                    //Json.toJSONString(resultMap).getBytes()
                                    //通过jackson返回json数据
                                    new ObjectMapper().writeValueAsBytes(resultMap)
                            )
                    )
            );
        }

        //放行操作
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
