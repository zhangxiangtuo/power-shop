package com.zxt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/*
     授权服务器
        1、声明token 存储位置
            tokenStore
        2、声明第三方资源账号信息
 */
@Configuration
//开启授权服务器，也可以将他放到引导类中
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;




    /*
        第三方和账号信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        /*
            授权方式：
                1. 授权码方式 authorization_code
                    登录后，通过请求获取授权码(code)，再通过code码获取token令牌
                        申请code码的地址(授权)
                            get http://localhost:50001/oauth/authorize?response_type=code&client_id=web&state=bjpowernode&redirect_uri=https://www.bilibili.com
                            通过重定向的地址，获取code码
                                https://www.bilibili.com/?code=UFwwAI&state=bjpowernode
                        通过code码来获取token令牌(颁发令牌)
                            post http://localhost:50001/oauth/token?grant_type=authorization_code&code=UFwwAI&redirect_uri=https://www.bilibili.com
                                需要在请求头中设置信息
                                    Authorization = Basic d2ViOndlYi1zZWNyZXQ= (web:web-secret的base64的编码转换)
                                获取到的token信息
                                    {
                                        "access_token": "a4f9dc1c-c84a-40f9-b139-dfd0ca6bbd94",
                                        "token_type": "bearer",
                                        "expires_in": 7199,
                                        "scope": "web-scopes"
                                    }
                2. ***密码授权*** password
                    通过客户端的账号和密码、第三方的账号和密码，完成授权和获取令牌的操作
                        post http://localhost:50001/oauth/token
                            请求参数
                                grant_type = password
                                username = zhangsan (数据库)
                                password = 123 (数据库)
                            请求头
                                Authorization = Basic YXBpOmFwaS1zZWNyZXQ= (api:api-secret的base64的编码转换)
                3. 静默授权 implicit
                    登录后，通过静默授权的方式，直接获取token令牌
                        申请token的请求地址
                            get http://localhost:50001/oauth/authorize?response_type=token&client_id=ios&state=bjpowernode&redirect_uri=https://www.bilibili.com
                            获取成功后，跳转到重定向地址，token令牌就在当前的地址栏中
                                https://www.bilibili.com/#access_token=a7c1deaa-e762-403d-81b1-4f01c815f27f&token_type=bearer&state=bjpowernode&expires_in=7199&scope=ios-scopes
                4. ***客户端授权*** client_credentials
                    给整个的客户端进行授权，而不是单独一个用户，相当于所有的用户权限都是相同的
                    客户端授权，并不需要用户名和密码(数据库)，但是需要第三方的账号信息
                        post http://localhost:5001/oauth/token
                            请求参数：
                                grant_type = client_credentials
                            请求头
                                Authorization = Basic Y2xpZW50OmNsaWVudC1zZWNyZXQ= (client:client-secret的base64的编码转换)
         */

        //配置第三方的账号信息

        clients.inMemory()

                /*
                    授权码授权，code授权
                 */
                //第三方的用户名称
                .withClient("web")
                //第三方的密钥
                .secret(passwordEncoder.encode("web-secret"))
                //配置当前账号的作用域
                .scopes("web-scopes")
                //配置第三方的授权方式，授权码方式
                .authorizedGrantTypes("authorization_code")
                //设置tokne的过期时间
                .accessTokenValiditySeconds(7200)
                //设置重定向的地址
                .redirectUris("https://www.bilibili.com")
            .and()

                /*
                    静默授权
                 */
                .withClient("ios")
                .secret(passwordEncoder.encode("ios-secret"))
                //设置过期时间 2h
                .accessTokenValiditySeconds(7200)
                //作用域
                .scopes("ios-scopes")
                //授权方式：静默授权
                .authorizedGrantTypes("implicit")
                .redirectUris("https://www.bilibili.com")
            .and()

                /*
                    密码授权
                 */
                .withClient("api")
                .secret(passwordEncoder.encode("api-secret"))
                .scopes("api-scopes")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(7200)
                .redirectUris("https://www.bilibili.com")
            .and()

                /*
                   客户端授权
                */
                .withClient("client")
                .secret(passwordEncoder.encode("client-secret"))
                .scopes("client-scopes")
                .authorizedGrantTypes("client_credentials")
                .accessTokenValiditySeconds(7200)
                .redirectUris("https://www.bilibili.com")

        ;
    }

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置Jwt的签名
        jwtAccessTokenConverter.setSigningKey("zxt");
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore(){
        //使用redis来存储token令牌
        //配置redis的连接工厂对象
        //return new RedisTokenStore(redisConnectionFactory);


        //通过Jwt来生成令牌

       /* {
            "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjU1NjA4MDQsInVzZXJfbmFtZSI6IjEiLCJhdXRob3JpdGllcyI6WyJzeXM6dXBkYXRlIiwic3lzOnF1ZXJ5Il0sImp0aSI6ImNmMGY0NTVmLWY4OTYtNDUwYy04MGUyLWE4Mjk5MzgwNjA2YSIsImNsaWVudF9pZCI6ImFwaSIsInNjb3BlIjpbImFwaS1zY29wZXMiXX0.dD8ZygcYgSjWRfLftQ1xLAfkdn2BAs4FtGooXGoabG0",
                "token_type": "bearer",
                "expires_in": 7199,
                "scope": "api-scopes",
                "jti": "cf0f455f-f896-450c-80e2-a8299380606a"
        }*/
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /*
        声明存储token的配置
            声明RedisTokenStore，将生成后的令牌存储到redis中
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //tokenStore需要在上面有一个案列
        endpoints
                //配置tokenStore设置令牌存储的容器，Redis
                .tokenStore(tokenStore())
                //密码授权方式，必须使用认证管理器
                .authenticationManager(authenticationManager)
                //设置Jwt生成令牌的方式
                .accessTokenConverter(jwtAccessTokenConverter())
        ;
    }
}
