package com.zxt.mapper;

import com.zxt.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DELL
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2022-10-10 19:39:15
* @Entity com.zxt.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




