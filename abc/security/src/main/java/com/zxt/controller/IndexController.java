package com.zxt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author zhangxiangtuo
 * @date 2022/10/10 16:37
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String success(){
        return "登录成功";
    }

    @GetMapping("/err")
    public String err(){
        return "登录失败";
    }

    //设置不同角色的权限，限制其对方法的调用，需要在启动类加注解
    @GetMapping("/getUserInfo1")
    public Object getUserInfo1(Principal principal){
       return principal;
    }

    @GetMapping("/getUserInfo2")
    public Object getUserInfo2(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/getUserInfo3")
    public Object getUserInfo3(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /*
         方法级别的访问权限控制
            1、在引导类中添加新的注解
                @EnableGlobalMethodSecurity(prePostEnabled = true)
            2、在控制器方法中添加新的注解
                @PreAuthorize()
                    hasAuthority：必须匹配的权限
                    hasAnyAuthority：匹配任意一个权限
                    hasRole：必须匹配的角色，需要ROLE_前缀信息
                    hasAnyRole：匹配任意一个角色
                    isAuthenticated：只需要登录认证之后就可以访问权限
     */

    @GetMapping("save")
    @PreAuthorize("hasAuthority('sys:save')")
    public String save(){
        return "新增操作";
    }

    @GetMapping("del")
    @PreAuthorize("hasAuthority('sys:del')")
    public String del(){
        return "删除操作";
    }

    @GetMapping("update")
    @PreAuthorize("hasAuthority('sys:update')")
    public String update(){
        return "修改操作";
    }

    @GetMapping("query")
    @PreAuthorize("hasAuthority('sys:query')")
    public String query(){
        return "查询操作";
    }


    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String list(){
        return "查询列表";
    }

    @GetMapping("findOne")
    @PreAuthorize("isAuthenticated()")
    public String findOne(){
        return "查询一个用户";
    }

}
