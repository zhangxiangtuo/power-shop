package com.zxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @TableName sys_user
 */

@TableName(value ="sys_user")
@Data
public class User implements Serializable, UserDetails {
    /**
     * 
     */
    @TableId(value = "userid", type = IdType.AUTO)
    private Integer userid;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "userpwd")
    private String userpwd;

    /**
     * 
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 
     */
    @TableField(value = "address")
    private String address;

    /**
     * 用户状态字段(1:正常 0:禁用)
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    /*
        权限的集合
     */
    @TableField(exist = false)
    private List<String> authorities = new ArrayList<>();

    /*
        将权限信息封装成集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        ArrayList<SimpleGrantedAuthority> auths = new ArrayList<>();
//        authorities.forEach(
//                auth ->  auths.add(new SimpleGrantedAuthority(auth))
//        );
//        return auths;

        List<SimpleGrantedAuthority> authorityList = authorities.stream().map(auth -> new SimpleGrantedAuthority(auth)).collect(Collectors.toList());
        return authorityList;
    }


    @Override
    public boolean isAccountNonExpired() {
        return status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return  status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return  status == 1;
    }

    @Override
    public boolean isEnabled() {
        return  status == 1;
    }

    public String getUsername() {
        //return this.username;
        return Integer.toString(this.userid);
    }

    @Override
    public String getPassword() {
        return this.userpwd;
    }

    /*
        我们可以给集合进行赋值
            但是Security中，需要我们的实体类(用户对象)实现一个接口UserDetails接口
            该接口就是最终通过数据库授权完成后，校验的实体类
     */
    //@TableField(exist = false)
    //private List<Role> roleList;
    //@TableField(exist = false)
    //private List<Permission> permissionList;

}