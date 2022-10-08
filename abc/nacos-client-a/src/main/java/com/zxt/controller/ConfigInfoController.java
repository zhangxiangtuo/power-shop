package com.zxt.controller;

import com.zxt.entity.R;
import com.zxt.properties.ConfigInfoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author zhangxiangtuo
 * @date 2022/10/8 19:22
 */
@RestController
@RequestMapping("/config")
public class ConfigInfoController {

    @Autowired
    private ConfigInfoProperties configInfoProperties;

    @GetMapping("/getInfo")
    public  HashMap<String, Object> getInfo(){

        HashMap<String, Object> map = new HashMap<>();
        map.put("name",configInfoProperties.getName());
        map.put("group",configInfoProperties.getGroup());
        map.put("version",configInfoProperties.getVersion());

        return map;

    }

    public <T> R ok(T data) {
        return R.builder().code(2000).msg("请求成功").success(true).data(data).build();
    }

}
