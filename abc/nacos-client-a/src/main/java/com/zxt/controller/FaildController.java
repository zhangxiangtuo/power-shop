package com.zxt.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import com.zxt.feign.ActivityServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxiangtuo
 * @date 2022/10/9 9:19
 */
@Slf4j
@RestController
@RequestMapping("/fail")
public class FaildController {

    @Autowired
    private ActivityServiceFeign activityServiceFeign;

    /**
     *  @HystrixCommand 熔断配置的注解
     *      fallbackMethod：异常熔断的方法名称配置
     *          异常熔断的方法必须和原方法的参数列表和返回值保持一致
     *          （就是说还需要一个一模一样的方法f01FallBackMethod）
     *          当调用的方法出现异常时，我们会快速的回调熔断方法，将结果返回不在阻塞线程
     *          当调用的方法出现阻塞时，默认的响应时间为1秒中，如果超过1秒中没有响应结果，就会自动熔断
     *          如果响应时间在1s以上，需要同过其他的配置进行自定以配置
     *        commandProperties：其他的配置信息，自定义配置
     */
    @GetMapping("/01")
    //可以查看注解的方法
    @HystrixCommand(fallbackMethod = "f01FallBackMethod")
    public String f01(){
        return  activityServiceFeign.err01();
    }

    public String f01FallBackMethod(){
        log.error("f01方法被熔断了");
        return "服务器出现异常请稍后重试";
    }


    @GetMapping("/02")
    @HystrixCommand(
            fallbackMethod = "f02FallBackMethod",
            commandProperties = {
                    //是否开启断路器
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED, value = "true"),
                    //超过2s未响应的请求，就会自动熔断
                    //@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value ="3000")
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS,value = "3000")
            }
    )
    public String f02(){
        //直接A熔断了（Read timed out）
        return   activityServiceFeign.err02();
    }

    public String f02FallBackMethod(){
        log.error("f02方法被熔断了");
        return "当前网络信号异常，请稍后重试";
    }

    /**
     * 断路器：
     *       好比保险丝开关，如果报喜纳斯熔断会有什么后果？家里没有电了
     *       断路器默认关闭的，所有的请求正常访问
     *       如果出现了异常，且异常达到统计阈值，就会将断路器打开，此时所有请求全部拒绝访问(即使正常的请求也是不允许访问的)
     *              统计数量、统计时间、异常比例
     *                  当请求的数量超过10次，且异常比列超过50%，我们就在统计时间5s内，无法进行正常的请求访问
     * @return
     */

    @GetMapping("/03/{id}")
    @HystrixCommand(
            fallbackMethod = "f03FallBackMethod",
            commandProperties = {
                    //断路器的开关设置，关闭的话是允许所有的请求访问
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED,value = "true"),
                    //断路器请求数量统计
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,value ="10"),
                    //断路器的统计时间
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS,value ="5000"),
                    //断路器的异常比例,取值范围是 0 ~ 100 之间，代表百分之几
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,value ="50")
                    //当满足上述规则，就会将断路器打开，此时所有的请求都不允许访问，当统计时间过去之后，如果恢复了正常访问，那么断路器也会放行一部分请求
                    //如果没有问题，则关闭断路器，如果还是有异常出现，并满足上述阈值，则继续开启断路器
            }
    )
    public String f03(@PathVariable("id")Integer id){
        return   activityServiceFeign.err03(id);
    }

    public String f03FallBackMethod(Integer id){
        log.error("f03方法被熔断了");
        return "当前信号弱，请移动到信号好一些的地方";
    }

}
