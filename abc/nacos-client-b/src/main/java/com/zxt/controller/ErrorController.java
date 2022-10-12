package com.zxt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxiangtuo
 * @date 2022/10/9 9:14
 */
@RestController
@RequestMapping("/err")
public class ErrorController {

    @GetMapping("/01")
    public String erro1(){
        //模拟除0异常
        int  i=1/0;
        return "success";
    }

    @GetMapping("/02")
    public String erro2() throws InterruptedException {
        //模拟超时异常
        Thread.sleep(5000);
        return "success";
    }

    @GetMapping("/03/{id}")
    public String erro3(@PathVariable("id") Integer id){
        if(id<=0){
            int i = 1/0;
        }
        return "success";
    }
}
