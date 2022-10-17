package com.zxt.shop.mapper;

import com.zxt.shop.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author DELL
* @description 针对表【sys_user(系统用户)】的数据库操作Mapper
* @createDate 2022-10-16 19:37:54
* @Entity com.zxt.shop.domain.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select({
            "select distinct m.perms from sys_menu m left join sys_role_menu rm on m.menu_id = rm.menu_id left join sys_user_role ur on rm.role_id = ur.role_id where ur.user_id = #{userId} and m.type = 2"
    })
    List<String> selectMenuPermsByUserId(Long userId);
}




