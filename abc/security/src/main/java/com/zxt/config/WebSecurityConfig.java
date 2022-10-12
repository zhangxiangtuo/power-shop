package com.zxt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zhangxiangtuo
 * @date 2022/10/10 16:46
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*
        Authentication(身份认证)
        多用户的配置信息
            通过内存方式存储用户：inMemoryAuthentication
            通过数据库库的方式查询用户
         无论通过那种方式进行校验，我们都需要配置一个密码加密器
            BCryptPasswordEncoder,不可逆的加密算法，对于相同的字符串，没出产生的密文都是不一样的
            能够保证密码的安全性
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //通过数据库来进行查询用户信息
        auth.userDetailsService(userDetailsService)
        //在内存中配置用户信息，可以配置多个用户信息
           //     .inMemoryAuthentication()
           //     //配置用户信息
           //     .withUser("root")
           //     .password(passwordEncoder().encode("root"))
           //     //给当前的用户设置角色信息
           //     .roles("ADMIN")
           //     //给当前用户设置权限信息，当角色和权限都存在时，我们认证时，需要通过权限去认证
           //     .authorities("sys:save", "sys:del", "sys:update", "sys:query")
           //     //如果有多个用户配置信息 + and
           //.and()
           //     .withUser("admin")
           //     .password(passwordEncoder().encode("admin"))
           //     //给admin配置了相同的ADMIN角色同样具有增删改查的权限
           //     .roles("ADMIN")
           //.and()
           //     .withUser("test")
           //     .password(passwordEncoder().encode("test"))
           //     .roles("TEST")
           //     .authorities("sys:save", "sys:query")
        ;
    }


    //通过配置类的方式实现授权操作
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //启动成功or失败的跳转页面
        http.formLogin().successForwardUrl("/").failureForwardUrl("/err")
                //设置需要授权的请求操作
                //通过配置实现需要自己手写登录
                //自定义登录页面
                //.loginPage()
                //如果登陆页面的表单提交的参数，不是username和password，你需要自定义进行指定
                /*
                   <form action=... method=...>
                       <input name="loginAct">
                       <input name="loginPwd">
                   </form>
                */
                //.usernameParameter("loginAct")
                //.passwordParameter("loginPwd")

                //.and()
                //.authorizeRequests()
                ////配置授权的请求以及所需要访问的权限
                //.antMatchers("/save").hasAuthority("sys:save")
                //.antMatchers("/del").hasAuthority("sys:del")
                //.antMatchers("/update").hasAuthority("sys:update")
                //.antMatchers("/query").hasAuthority("sys:query")
                ////配置授权的请求以及所需要访问角色
                ////不需要以ROLE_开头，通过配置中加载角色信息，都不用
                //.antMatchers("/list").hasAuthority("ADMIN")
                ////当前路径请求，登录成功后即可访问
                ////.antMatchers("/findOne").authenticated()
                ////上述请求除外，其他请求只要登录后即可访问
                //.anyRequest().authenticated()
        ;
    }


    //BCryptPasswordEncoder密码加密
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(encoder.encode("123"));
            /*
                $2a$10$se2jseURsa.tJW5Z68BAl.aqILUG8I/JE3hZhvr1S8RUr2uXS23M2
                $2a$10$t4l66Ejoof9VUdAbynBawu83Z0hG1Q/eYVKlF7EQ.njvwnYm3VjK.
                $2a$10$xF2tEzrYoNkPFrR8AWt.g.VH87lEYql0KaCI3qJCXpsC1SsBcRcDO
                $2a$10$97uuguaRbBGj/4w0DUWr3eRv3GeFf.gw1T/nOHlANa1rHkIncl4wK
                $2a$10$hL6ms/NiWlatchdGGDe2S.bCQL8VEtJvjnasnvlgK8LogsfyS.zk.
                $2a$10$OveSrHUaigLjbLD8et.lBe0KsmUxtZt.zga9k2ytXstQGCFSfN5Wi
                $2a$10$3YMWdlOsqvzk2KqbAIR8Te5zyzR3zFtICDi61xx91qALR2TsgVrDO
                $2a$10$Tw6eWkhb.PTVoo6AnEHQeOonKmCqPD1ePbxJDbwZlSOS0bCllVvDe
                $2a$10$WYVW1xLZ3mM0NxXpxbP6te4RfassXmg70kC88OUVxeJvzCIPuGvoO
                $2a$10$ewco.jF9leHoVltn7xv.POEd/CbFAAH27d2BhHZyhyz.TzTjiskLy
             */
            //用框架进行校验
            System.out.println(encoder.matches("123","$2a$10$se2jseURsa.tJW5Z68BAl.aqILUG8I/JE3hZhvr1S8RUr2uXS23M2"));
            System.out.println(encoder.matches("123","$2a$10$t4l66Ejoof9VUdAbynBawu83Z0hG1Q/eYVKlF7EQ.njvwnYm3VjK."));
            System.out.println(encoder.matches("123","$2a$10$xF2tEzrYoNkPFrR8AWt.g.VH87lEYql0KaCI3qJCXpsC1SsBcRcDO"));
            System.out.println(encoder.matches("123","$2a$10$97uuguaRbBGj/4w0DUWr3eRv3GeFf.gw1T/nOHlANa1rHkIncl4wK"));
            System.out.println(encoder.matches("123","$2a$10$hL6ms/NiWlatchdGGDe2S.bCQL8VEtJvjnasnvlgK8LogsfyS.zk."));
            System.out.println(encoder.matches("123","$2a$10$OveSrHUaigLjbLD8et.lBe0KsmUxtZt.zga9k2ytXstQGCFSfN5Wi"));
            System.out.println(encoder.matches("123","$2a$10$3YMWdlOsqvzk2KqbAIR8Te5zyzR3zFtICDi61xx91qALR2TsgVrDO"));
            System.out.println(encoder.matches("123","$2a$10$Tw6eWkhb.PTVoo6AnEHQeOonKmCqPD1ePbxJDbwZlSOS0bCllVvDe"));
            System.out.println(encoder.matches("123","$2a$10$WYVW1xLZ3mM0NxXpxbP6te4RfassXmg70kC88OUVxeJvzCIPuGvoO"));
            System.out.println(encoder.matches("123","$2a$10$ewco.jF9leHoVltn7xv.POEd/CbFAAH27d2BhHZyhyz.TzTjiskLy"));


    }
}
