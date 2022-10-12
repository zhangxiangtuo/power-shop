package com.zxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName sys_role
 */

@TableName(value ="sys_role")
@Data
public class Role implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "roleid")
    private Integer roleid;

    /**
     * 
     */
    @TableField(value = "rolename")
    private String rolename;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}