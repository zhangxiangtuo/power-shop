package com.zxt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxt.domain.Role;
import com.zxt.service.RoleService;
import com.zxt.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2022-10-10 19:39:15
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




