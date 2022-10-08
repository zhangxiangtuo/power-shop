package com.zxt.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxt.entity.R;
import com.zxt.domain.Activity;
import com.zxt.springcloud.mapper.ActivityMapper;
import com.zxt.springcloud.service.ActivityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 15:29
 */
/*
    通过MybatisPlus来操作单表的CRUD操作
        官网：https://baomidou.com/
        MybatisPlus是对Mybatis的增强，不做改变，提高效率、简化代码。
        内置通用Mapper
            mapper.ActivityMapper
                interface ActivityMapper extends BaseMapper<Activity>
        内置通用Service
            service.ActivityService
                interface ActivityService extends IService<Activity>
            service.impl.ActivityServiceImpl
                class ActivityServiceImpl extends ServiceImpl<ActivityMapper,Activity> implements ActivityService
        实现完成之后，我们可以注入service或mapper对象操作单表的CRUD操作
            通过Service和通用Mapper的区别是啥呢？
                service可以批量进行插入和更新操作，而mapper没有
        在实体类上通过注解的方式来声明实体类和数据库表的关系
            @TableName
            @TableId
            @TableField
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityMapper activityMapper;


    //查询所有
    @GetMapping("/list")
    public List<Activity> list() {
        //service查询所有
        List<Activity> list = activityService.list();
        //以下三种遍历方式都可以
       /* for (Activity activity : list) {
            System.out.println(activity);
        }*/
        list.forEach(System.out::println);

       /* list.forEach(
                activity -> {
                    System.out.println(activity);
                }
        );*/

        //mapper查询所有
        activityMapper.selectList(null).forEach(System.out::println);

        return list;
    }

    //根据条件查询
    @GetMapping("/listCondition/{name}")
    public List<Activity> listByCondition(@PathVariable("name") String name) {
        //根据名称进行模糊查询
        List<Activity> activitiesList = activityService.list(
                //分装查询的条件
                new QueryWrapper<Activity>().like(StringUtils.isNotBlank(name), "name", name)
        );
        activitiesList.forEach(System.out::println);

        System.out.println("-----------------------------");

        activityMapper.selectList(
                //分装查询的条件
                new QueryWrapper<Activity>().like(StringUtils.isNotBlank(name), "name", name)
        ).forEach(System.out::println);

        return activitiesList;
    }

    //根据id查询一条数据
    @GetMapping("/getOne/{id}")
    public Activity getOne(@PathVariable("id") String id) {
        Activity activity = activityService.getOne(
                new QueryWrapper<Activity>().eq(StringUtils.isNotBlank(id), "id", id)
        );

        Activity one = activityService.getOne(
                new LambdaQueryWrapper<Activity>()
                        .eq(StringUtils.isNotBlank(id), Activity::getId, id)
        );
        System.out.println(one);

        System.out.println("-----------");

        System.out.println(activity);

        System.out.println("-----------");

        //mapper查询
        System.out.println(activityMapper.selectOne(
                new QueryWrapper<Activity>().eq(StringUtils.isNotBlank(id), "id", id)
        ));

        return activity;
    }


    //批量新增
    @PostMapping("/saveBatch")
    public Boolean saveBatch(@RequestBody List<Activity> activityList) {

        Boolean flag = activityService.saveBatch(activityList);

        //Mapper只能for循环进行插入，但是不推荐这样做
        //activityList.forEach(a -> activityMapper.insert(a));
        return flag;
    }

    //修改或者新增
    @PostMapping("/saveOrUpdate")
    public Boolean saveOrUpdate(@RequestBody Activity activity) {
        //新增或者更新，如果有id就是更新，如果没有id就是新增
        Boolean flag = activityService.saveOrUpdate(activity);
        //Mapper只能for循环进行插入，但是不推荐这样做
        //activityList.forEach(a -> activityMapper.insert(a));
        return flag;
    }


    //根据id修改
    @PutMapping("/update")
    public Boolean update(@RequestBody Activity activity) {
        Boolean flag = activityService.updateById(activity);
        //int a = activityMapper.updateById(activity);
        return flag;
    }


    //条件修改
    @PutMapping("updateCondition/{owner}")
    public Boolean updateCondition(@PathVariable("owner") String owner, @RequestBody Activity activity) {
        Boolean flag = activityService.update(
                activity,
                new LambdaQueryWrapper<Activity>()
                        //根据owner更新数据
                        .eq(StringUtils.isNotBlank("owner"), Activity::getOwner, owner)
        );
        return flag;
    }


    //删除操作（单）
    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteById(@PathVariable("id") String id) {
        //activityMapper.deleteById(id);
        return activityService.removeById(id);
    }

    //删除操作（多个id）
    @DeleteMapping("/deleteByIds")
    public Boolean deleteByIds(@RequestBody List<String> ids) {
        //activityMapper.deleteById(id);
        return activityService.removeByIds(ids);
    }

    //根据条件删除
    @DeleteMapping("/deleteByCondition/{owner}")
    public Boolean deleteByCondition(@PathVariable("owner") String owner) {

        return
                activityService.remove(
                        new LambdaQueryWrapper<Activity>()
                                .eq(StringUtils.isNotBlank("owner"), Activity::getOwner, owner)
                )
                ;
    }

    /*
        Stream Api的常用方法：
            forEach 用于遍历，里面是Consumer，只需要传入一个参数，无须返回值
            filter 用于数据的过滤Predicate，只需要传入一个参数，得到一个返回值boolean
            map 用于数据收集，只需要传入一个参数，返回一个数据
            reduce 用于进行计算的，我们可以将数据进行加减乘除操作，并收集得到计算的结果
     */

    @GetMapping("/lambda")
    public List<Activity> lambda() {
        //构建集合或者通过service将集合数据查询出来
        List<Activity> activityList = activityService.list();

        //遍历操作
        activityList.forEach(a -> System.out.println(a));
        //activityList.forEach(System.out::println);
        System.out.println("------------");

        //将过滤条件为真的数据收集成一个集合
        List<Activity> list = activityList.stream().filter(a -> Integer.valueOf(a.getId()) <= 3).collect(Collectors.toList());
        list.forEach(a -> System.out.println(a));
        System.out.println("------------");

        //map 用于数据收集，只需要传入一个参数，返回一个数据
        //将集合中的id收集成一个集合
        List<String> activityList1 = activityList.stream().map(Activity::getId).collect(Collectors.toList());
        List<String> activityList2 = activityList.stream().map(a -> a.getId()).collect(Collectors.toList());

        activityList1.forEach(a -> System.out.println(a));
        System.out.println("------------");
        activityList2.forEach(a -> System.out.println(a));


        //将集合中没用的字段过滤过去
        List<Activity> collect = activityList.stream().map(a -> Activity.builder().owner(a.getOwner()).name(a.getName()).startDate(new Date().toString()).build()).collect(Collectors.toList());
        System.out.println("------------");
        collect.forEach(a -> System.out.println(a));

        //计算操作
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(integerList.stream().reduce((a, b) -> {
            System.out.println("a :" + a);
            System.out.println("b :" + b);
            return a + b;
        }).get());
        System.out.println("--------------");
        System.out.println(integerList.stream().reduce((a, b) -> a - b).get());


        return list;
    }


    public <T> R ok(T data) {
        return R.builder().code(2000).msg("请求成功").success(true).data(data).build();
    }
}
