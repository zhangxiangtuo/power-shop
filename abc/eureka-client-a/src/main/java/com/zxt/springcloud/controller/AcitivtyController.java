package com.zxt.springcloud.controller;

import com.zxt.domain.Activity;
import com.zxt.entity.R;
import com.zxt.springcloud.feign.ActivityServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 20:50
 */
@RestController
@RequestMapping("/activity")
public class AcitivtyController {
    /**
     * Feign声明
     * 0. 在引导类上添加新的注解@EnableFeignClients
     * 1. 创建一个接口，在该接口上添加新注解@FeignClient("远程调用微服务名称")
     * 2. 在接口中声明控制器方法，不需要方法体的实现
     * 该方法就是远程调用的方法，声明的方法的参数列表、返回值和请求地址必须和调用方法一致
     * 通过不同的注解，代表不同的参数传递方式
     * Feign
     *
     * @RequestParam代表通过地址栏键值对的方式来传递参数
     * @PathVariable代表通过地址栏占位符的方式来传递参数
     * @RequestBody代表通过json的方式的进行传递参数
     * @GetMapping发送get的请求
     * @PostMapping发送post的请求
     * @PutMapping发送put的请求
     * @DeleteMapping发送delete的请求 Controller
     * @RequestParam代表通过地址栏键值对的方式来接收参数
     * @PathVariable代表通过地址栏占位符的方式来接收参数
     * @RequestBody代表通过json的方式的进行接收参数
     * @GetMapping接收get的请求
     * @PostMapping接收post的请求
     * @PutMapping接收put的请求
     * @DeleteMapping接收delete的请求 请求方式发送
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping 3. 在控制器中，直接引入接口，然后通过对象调用方法，即可发送远程的请求
     * Feign和RestTemplate的对比：
     * 它们同样是发送http的restful方式的请求，最大的不同就是：
     * RestTemplate的put和delete的请求方式，无法接收返回值(void)
     * Feign的put和delete的请求方式，可以接收参数的
     * <p>
     * Feign是已经内置Ribbon操作，可以直接进行负载均衡的调用，和RestTemplate需要添加注解这点不同
     * <p>
     * 那么这两个哪个用的多哪个用的少呢？
     * Feign我们主要使用调用自己的微服务的
     * RestTemplate可以调用其他人的微服务，如果当前微服务接口调用较少懒得声明Feign，也可以使用RestTemplate
     */

    @Autowired
    private ActivityServiceFeign activityServiceFeign;

    @GetMapping("/01")
    public R list01() {
        return ok(
                activityServiceFeign.list()
        );
    }

    @GetMapping("/02")
    public R list02() {
        return ok(
                activityServiceFeign.listByCondition("111")
        );
    }

    @GetMapping("/03")
    public R one() {
        return ok(
                activityServiceFeign.getOne("1")
        );
    }

    @GetMapping("/04")
    public R saveBatch() {
        List<Activity> activityList = Arrays.asList(
                Activity.builder().name("66").owner("66").startDate(new Date().toString()).endDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build(),
                Activity.builder().name("77").owner("77").startDate(new Date().toString()).endDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build(),
                Activity.builder().name("88").owner("88").startDate(new Date().toString()).endDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build(),
                Activity.builder().name("99").owner("99").startDate(new Date().toString()).endDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build(),
                Activity.builder().name("00").owner("00").startDate(new Date().toString()).endDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build()
        );

        return ok(
                activityServiceFeign.saveBatch(activityList)
        );
    }


    @GetMapping("/05")
    public R saveOrUpdate() {
        return ok(
                activityServiceFeign.saveOrUpdate(
                        Activity.builder().name("123").owner("123").startDate(new Date().toString()).endDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build()
                )
        );
    }

    @GetMapping("/06")
    public R update(){
        return ok(
                activityServiceFeign.update(
                        Activity.builder().id("1578385950210330625").name("312").owner("321").startDate(new Date().toString()).endDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build()
                )
        );
    }

    @GetMapping("/07")
    public R updateCondition(){
        return ok(
                activityServiceFeign.updateCondition(
                        "111",
                        Activity.builder().name("123").startDate(new Date().toString()).endDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build()
                )
        );
    }

    @GetMapping("/08")
    public R delete(){
        return ok("1578385950227107842");
    }

    @GetMapping("/09")
    public R deleteByIds(){
        return ok(
                activityServiceFeign.deleteByIds(
                        Arrays.asList("5","6")
                )
        );
    }


    @GetMapping("/10")
    public R deleteCondition(){
        return ok(
                activityServiceFeign.deleteByCondition("aaa")
        );
    }








    public <T> R ok(T data) {
        return R.builder().code(2000).msg("请求成功").success(true).data(data).build();
    }
}
