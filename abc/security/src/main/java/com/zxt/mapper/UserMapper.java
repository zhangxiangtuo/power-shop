package com.zxt.mapper;

import com.zxt.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author DELL
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2022-10-10 19:39:15
* @Entity com.zxt.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select({
            "select p.percode from sys_permission p left join sys_role_permission rp on p.id = rp.perid",
            "left join sys_user_role ur on ur.roleid = rp.roleid",
            "where ur.userid = #{userid}",
    })
    List<String> getUserAuthorityList(Integer userid);
}




