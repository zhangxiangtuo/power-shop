package com.zxt.shop.service.impl;

import com.alibaba.nacos.api.remote.request.Request;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxt.shop.constants.AuthServerConstants;
import com.zxt.shop.domain.SysUser;
import com.zxt.shop.service.SysUserService;
import com.zxt.shop.mapper.SysUserMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
* @author DELL
* @description 针对表【sys_user(系统用户)】的数据库操作Service实现
* @createDate 2022-10-16 19:37:54
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService, UserDetailsService {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //我们后台，需要区分哪个请求是来自后台管理器系统的，哪个是来自商城的
        /*
            我们前端发送请求时，会在请求头中标记来源
                loginType = sysUser 代表的是后台系统管理的用户
                loginType = user 代表的是前台商城用户
         */
        //获取当前请求的请求头信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        String loginType = request.getHeader(AuthServerConstants.LOGIN_TYPE);

        switch (loginType){
            //sysUser 代表的是后台系统管理的用户
            case AuthServerConstants.SYS_USER:
                //根据用户名查询账号信息
                SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                        .eq(StringUtils.isNotBlank(username), SysUser::getUsername, username)
                );

                if(!ObjectUtils.isEmpty(sysUser)){
                    //获取权限信息
                    List<String> authorities = sysUserMapper.selectMenuPermsByUserId(sysUser.getUserId());
                    //封装到实体类中
                    if(CollectionUtils.isEmpty(authorities))
                        authorities = new ArrayList<>();

                    sysUser.setAuths(authorities);
                    return sysUser;
                }
            case AuthServerConstants.USER:
        }
        //权限认证未通过
        return null;
    }
}




