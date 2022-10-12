package com.zxt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/*
       SpringSecurity的配置类
            1、配置登录的UserDetailService 通过数据库来查询用户信息
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    /*
        通过UserDetailService，来进行数据库的权限检验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    /*
        SpringSecurity中对密码的校验和加密，需要使用密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
        设置全局的访问权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //通过默认的表单进行登录授权操作
        http.formLogin()
                .and()
                //所有的请求登录后即可访问
                .authorizeRequests().anyRequest().authenticated();
    }


    /*
        注入认证管理器，交给Spring进行管理
            密码授权方式必须添加的Bean对象
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
