package com.zxt.feign;

import com.zxt.domain.Activity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 21:01
 */


//远程调用nacos-client-b
@FeignClient("nacos-client-b")
public interface ActivityServiceFeign {

     @GetMapping("/activity/list")
     List<Activity> list();

     //根据条件查询
     @GetMapping("/activity/listCondition/{name}")
     List<Activity> listByCondition(@PathVariable("name") String name);


     @GetMapping("/activity/getOne/{id}")
     Activity getOne(@PathVariable("id") String id);

     //批量新增
     @PostMapping("/activity/saveBatch")
     Boolean saveBatch(@RequestBody List<Activity> activityList);


     //修改或者新增
     @PostMapping("/activity/saveOrUpdate")
     Boolean saveOrUpdate(@RequestBody Activity activity);

     //根据id修改
     @PutMapping("/activity/update")
     Boolean update(@RequestBody Activity activity);

     //条件修改
     @PutMapping("/activity/updateCondition/{owner}")
     Boolean updateCondition(@PathVariable("owner") String owner, @RequestBody Activity activity);

     //删除操作（单）
     @DeleteMapping("/activity/deleteById/{id}")
     Boolean deleteById(@PathVariable("id") String id);

     //删除操作（多个id）
     @DeleteMapping("/activity/deleteByIds")
     Boolean deleteByIds(@RequestBody List<String> ids);

     @DeleteMapping("/activity/deleteByCondition/{owner}")
     Boolean deleteByCondition(@PathVariable("owner") String owner);

}
