package com.zxt.springcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxt.domain.Activity;
import com.zxt.springcloud.mapper.ActivityMapper;
import com.zxt.springcloud.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 16:05
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

   /* @Override
    public boolean save(Activity entity) {
        return activityMapper.insert(entity) > 0;
    }*/
}
