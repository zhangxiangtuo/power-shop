package com.zxt.springcloud.controller;

import com.zxt.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 14:02
 */
@Slf4j
@RestController
@RequestMapping("/b")
public class BController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/01/{id}")
    public R b01(@PathVariable("id")Integer id,
                 @RequestParam("username")String username){

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("username",username);
        resultMap.put("port",port);

        return R.builder()
                .code(20000)
                .msg("请求成功")
                .success(true)
                .data(resultMap)
                .build();
    }

    @PostMapping("/02/{id}")
    public R b02(@PathVariable("id")Integer id,
                 @RequestParam("username")String username,
                 @RequestBody Map<String,Object> requestBody){

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("username",username);
        resultMap.put("requestBody",requestBody);
        resultMap.put("port",port);

        return R.builder()
                .code(20000)
                .msg("请求成功")
                .success(true)
                .data(resultMap)
                .build();
    }


    @PutMapping("/03/{id}")
    public R b03(@PathVariable("id") Integer id,
                 @RequestParam("username") String username,
                 @RequestBody Map<String,Object> requestBody){


        log.info("id : {}",id);
        log.info("username : {}",username);
        log.info("requestBody : {}",requestBody);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("username",username);
        resultMap.put("requestBody",requestBody);
        resultMap.put("port",port);


        return R.builder()
                .code(20000)
                .msg("请求成功")
                .success(true)
                .data(resultMap)
                .build();
    }

    @DeleteMapping("04/{id}")
    public R b04(@PathVariable Integer id){
        log.info("id : {}",id);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("id",id);
        resultMap.put("port",port);


        return R.builder()
                .code(20000)
                .msg("请求成功")
                .success(true)
                .data(resultMap)
                .build();
    }
}
