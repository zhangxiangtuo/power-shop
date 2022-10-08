package com.zxt.springcloud.controller;

import com.zxt.constants.UrlConstants;
import com.zxt.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 13:22
 */
@Slf4j
@RestController
@RequestMapping("/a")
public class AController {
    /*
        发送Restful方式的请求：
            Get
                ResponseEntity<T> getForEntity(String url,T responseType,Map uriVariables)
                T getForObject(String url,T responseType,Map uriVariables)
            Post
                ResponseEntity<T> postForEntity(String url,Object request,T responseType,Map uriVariables)
                T postForObject(String url,Object request,T responseType,Map uriVariables)
            Put
                void put(String url,Object request,Map uriVariables)
            Delete
                void delete(String url,Map uriVariables)

            get和post的请求方式都有两种，区别就是返回值不过而已
            put和delete是没有返回值的，如果想要接收带有返回值的请求，请使用get和post方式

            不同的请求方式，传递参数的方式也不相同，重点要搞清楚如何发送请求和如何接收传递的参数
                如果地址栏键值对的传递方式：@RequestParam...
                如果地址栏占位符的传递方式：@PathVariable...
                如果post或put方式的请求体传递方式：@RequestBody...
     */
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/01")
    public R sendGetRequest(){
        //封装地址栏占位符中的数据
        Map<String,Integer> uriVariables = new HashMap<>();
        uriVariables.put("id",111);

        /*ResponseEntity<R> entity = restTemplate.getForEntity(
                UrlConstants.RESTTEMPLATE_GET_01,
                R.class,
                uriVariables
        );

        log.info(
                "ResponseEntity StatusCode : {}",entity.getStatusCode()
        );
        log.info(
                "ResponseEntity StatusCodeValue : {}",entity.getStatusCodeValue()
        );
        log.info(
                "ResponseEntity Headers : {}",entity.getHeaders()
        );
        log.info(
                "ResponseEntity Body : {}",entity.getBody()
        );*/

        return  restTemplate.getForObject(
                UrlConstants.RESTTEMPLATE_GET_01,
                R.class,
                uriVariables
        );
    }

    @GetMapping("/02")
    public R sendPostRequest(){

        //封装地址栏占位符中的数据
        Map<String,Integer> uriVariables = new HashMap<>();
        uriVariables.put("id",222);

        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("name","laozhang");
        requestBody.put("age",55);
        requestBody.put("address","深圳市宝安区");

        ResponseEntity<R> entity = restTemplate.postForEntity(
                UrlConstants.RESTTEMPLATE_POST_02,
                requestBody,
                R.class,
                uriVariables
        );

        log.info(
                "ResponseEntity StatusCode : {}",entity.getStatusCode()
        );
        log.info(
                "ResponseEntity StatusCodeValue : {}",entity.getStatusCodeValue()
        );
        log.info(
                "ResponseEntity Headers : {}",entity.getHeaders()
        );
        log.info(
                "ResponseEntity Body : {}",entity.getBody()
        );

        return  restTemplate.postForObject(
                UrlConstants.RESTTEMPLATE_POST_02,
                requestBody,
                R.class,
                uriVariables
        );
    }

    @GetMapping("/03")
    public String sendPutRequest(){
        //封装地址栏占位符中的数据
        Map<String,Integer> uriVariables = new HashMap<>();
        uriVariables.put("id",333);

        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("name","老王");
        requestBody.put("age",55);
        requestBody.put("address","深圳市宝安区航程街道");

        restTemplate.put(
                UrlConstants.RESTTEMPLATE_PUT_03,
                requestBody,
                uriVariables
        );

        return "ok";
    }

    @GetMapping("/04")
    public String sendDeleteRequest(){
        //封装地址栏占位符中的数据
        Map<String,Integer> uriVariables = new HashMap<>();
        uriVariables.put("id",4444);
        restTemplate.delete(
                UrlConstants.RESTTEMPLATE_DELETE_04,
                uriVariables
        );
        return "ok";
     }
}
