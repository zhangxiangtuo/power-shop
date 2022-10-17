package com.zxt.shop.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxt.shop.constants.GatwayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/*
    网关的路由操作
        将登录操作直接访问auth-server，登录并申请token
        将获取到的token放到redis中去，然后返回到前端
 */
@Configuration
public class LoginRouter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes().route(
                "auth-server",r ->{
                    //当前请求地址未登录/oath/token时，直接放行访问auth-server进行获取token的登录操作
                    //当获取到了token信息之后，我们需要将他放到redis中
                    return r.path(GatwayConstants.WHITE_LIST.get(0))
                            .filters(f -> f.modifyResponseBody(String.class,String.class,((exchange, s) -> {
                                //s代表的时返回的json数据
                                /*
                                    {
                                        "access_token": "xxx",
                                        "token_type": "bearer",
                                        "expires_in": 7199,
                                        "scope": "web-scopes"
                                    }
                                 */
                                JSONObject jsonObject = JSON.parseObject(s);

                                if(jsonObject.containsKey(GatwayConstants.ACCESS_TOKEN) && jsonObject.containsKey(GatwayConstants.EXPIRES_IN)){
                                    //获取token和过期时间
                                    String accessToken = jsonObject.getString(GatwayConstants.ACCESS_TOKEN);
                                    Long expiresIn = jsonObject.getLong(GatwayConstants.EXPIRES_IN);

                                    //存入到redis中
                                    redisTemplate.opsForValue().set(
                                            GatwayConstants.OAUTH_JWT_PREFIX + accessToken,
                                            "",
                                            expiresIn,
                                            TimeUnit.SECONDS
                                    );
                                }

                                //原原本本的jason数据进行返回
                                return Mono.just(s);
                            })))
                            .uri("lb://auth-server");
                }
        ).build();
    }
}
