package com.zxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxt.domain.User;
import com.zxt.mapper.UserMapper;
import com.zxt.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
    /*
        登录时，会将当前用户名称传递到该方法中
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //通过Mapper将用户查询出来
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(StringUtils.isNotBlank(username),User::getUsername, username)
        );

        if(!ObjectUtils.isEmpty(user)){
            //查询权限
            List<String> authorityList = userMapper.getUserAuthorityList(user.getUserid());

            //存储到user对象的集合中，交给框架进行权限的转换操作
            if(CollectionUtils.isEmpty(authorityList))
                //直接给一个空的集合
                authorityList = new ArrayList<>();

            //无论有没有都给他设置到user当中
            user.setAuthorities(authorityList);

            return user;
        }
        return null;
    }
}




