package com.zxt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxt.domain.Permission;
import com.zxt.service.PermissionService;
import com.zxt.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【sys_permission】的数据库操作Service实现
* @createDate 2022-10-10 19:39:15
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




