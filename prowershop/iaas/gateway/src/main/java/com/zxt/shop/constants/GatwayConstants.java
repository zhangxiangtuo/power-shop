package com.zxt.shop.constants;

import java.util.Arrays;
import java.util.List;

public interface GatwayConstants {
    /*
       放行的白名单路径
    */
    List<String> WHITE_LIST = Arrays.asList("/oauth/token");

    /*
        token的请求头Authorization
     */
    String TOKEN_AUTHORIZATION_PREFIX = "Authorization";

    /*
        token请求头的部分的值，根据它来获取jwt信息
     */
    String TOKEN_BEARER_SUFFIX = "bearer ";


    /*
        token返回的json的key
     */
    String ACCESS_TOKEN = "access_token";

    /*
        token返回的json的过期的时间的key
     */
    String EXPIRES_IN = "expires_in";

    /*
        存入redis的token前缀
     */
    String OAUTH_JWT_PREFIX = "oauth:jwt:";
}
