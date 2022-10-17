package com.zxt.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;


@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*
        配置第三方账号的授权信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                /*
                    后端用户的授权
                 */
                .withClient("web")
                .secret(passwordEncoder.encode("web-secret"))
                .scopes("all")
                .accessTokenValiditySeconds(21600)
                .authorizedGrantTypes("password")
                .redirectUris("https://www.baidu.com")
                .and()
                /*
                    前台用户的授权，相当于给整个客户端进行授权
                 */
                .withClient("power")
                .secret(passwordEncoder.encode("power-secret"))
                .scopes("all")
                .accessTokenValiditySeconds(Integer.MAX_VALUE)
                .redirectUris("https://www.baidu.com")
        ;

    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        /*
            通过私钥来生成jwt
         */
        ClassPathResource resource = new ClassPathResource("rsa/sz2206.jks");
        KeyStoreKeyFactory keyFactorye =  new KeyStoreKeyFactory(resource , "sz2206".toCharArray());
        KeyPair keyPair = keyFactorye.getKeyPair("sz2206");

        jwtAccessTokenConverter.setKeyPair(keyPair);

        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    }
}
