package com.zxt.mapper;

import com.zxt.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【sys_permission】的数据库操作Mapper
* @createDate 2022-10-10 19:39:15
* @Entity com.zxt.domain.Permission
*/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}




