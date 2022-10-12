package com.zxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sys_permission
 */
@TableName(value ="sys_permission")
@Data
public class Permission implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "perid")
    private Integer perid;

    /**
     * 
     */
    @TableField(value = "pername")
    private String pername;

    /**
     * 
     */
    @TableField(value = "percode")
    private String percode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}