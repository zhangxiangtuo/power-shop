package com.zxt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxiangtuo
 * @date 2022/10/10 21:40
 */
public class JwtUtils {
    public static void main(String[] args) {
        /*
            eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
            .
            eyJleHAiOjE2NjU0MTMxMTcsInVzZXJJZCI6MSwiaWF0IjoxNjY1NDA5NTE3LCJ1c2VybmFtZSI6InpoYW5nc2FuIn0
            .
            f0Lt8fcznVgvfxebhPZN_bOTODs70D2MGYVcsJDaPMM
         */
        //System.out.println(createToken(1L));

        Map<String, Claim> map = verifyToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjU0MTMxMTcsInVzZXJJZCI6MSwiaWF0IjoxNjY1NDA5NTE3LCJ1c2VybmFtZSI6InpoYW5nc2FuIn0.f0Lt8fcznVgvfxebhPZN_bOTODs70D2MGYVcsJDaPMM");
        System.out.println("token过期时间 : "+map.get("exp").asDate());
        System.out.println("token签发时间 : "+map.get("iat").asDate());
        System.out.println("UserId : "+map.get("userId").asLong());
        System.out.println("Username : "+map.get("username").asString());

    }

    public static String  createToken(Long userId){
        // 设置header信息
        Map<String, Object> header = new HashMap<>(4);
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        String token = JWT.create().withHeader(header)
                .withClaim("userId", userId)
                .withClaim("username", "zhangsan")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 3600 * 1000))
                .sign(Algorithm.HMAC256("zxt"));

        return token;
    }

    public static Map<String, Claim> verifyToken(String token){
        //解析token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("zxt")).build();

        System.out.println(verifier.verify(token).getToken());
        System.out.println(verifier.verify(token).getHeader());
        System.out.println(verifier.verify(token).getPayload());
        System.out.println(verifier.verify(token).getSignature());

        return verifier.verify(token).getClaims();
    }

}
