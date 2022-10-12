package com.zxt.service.impl;

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxt.domain.User;
import com.zxt.service.UserService;
import com.zxt.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
* @author DELL
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2022-10-10 19:39:15
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    //登录时，会将当前用户名称传递到该方法中
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1、根据用户名，查询用户信息，并关联查询出所对应的权限信息
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(StringUtils.isNotBlank(username),User::getUsername,username)
        );

        System.out.println("User: " + user);

        if(!ObjectUtils.isEmpty(user)){
            //封装用户的权限信息
            List<String> authorityList = userMapper.getUserAuthorityList(user.getUserid());

            if(CollectionUtils.isEmpty(authorityList))
                authorityList =  new ArrayList<>();
            user.setAuthorities(authorityList);

            System.out.println("authorityList: " + authorityList);
            //返回user对象，交给SpringSecurity框架进行权限的校验操作
            //返回user对象，交给SpringSecurity框架进行权限的校验操作
            //返回user对象，交给SpringSecurity框架进行权限的校验操作
            //返回user对象，交给SpringSecurity框架进行权限的校验操作
            return user;
        }
        //当前用户不存在
        return null;
    }
}




