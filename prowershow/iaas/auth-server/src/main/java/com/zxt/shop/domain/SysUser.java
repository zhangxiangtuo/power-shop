package com.zxt.shop.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 系统用户
* @TableName sys_user
*/
public class SysUser implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long userId;
    /**
    * 用户名
    */
    @NotBlank(message="[用户名]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("用户名")
    @Length(max= 50,message="编码长度不能超过50")
    private String username;
    /**
    * 密码
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("密码")
    @Length(max= 100,message="编码长度不能超过100")
    private String password;
    /**
    * 邮箱
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("邮箱")
    @Length(max= 100,message="编码长度不能超过100")
    private String email;
    /**
    * 手机号
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("手机号")
    @Length(max= 100,message="编码长度不能超过100")
    private String mobile;
    /**
    * 状态  0：禁用   1：正常
    */
    @ApiModelProperty("状态  0：禁用   1：正常")
    private Integer status;
    /**
    * 创建者ID
    */
    @ApiModelProperty("创建者ID")
    private Long createUserId;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 用户所在的商城Id
    */
    @ApiModelProperty("用户所在的商城Id")
    private Long shopId;

    /**
    * 
    */
    private void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 用户名
    */
    private void setUsername(String username){
    this.username = username;
    }

    /**
    * 密码
    */
    private void setPassword(String password){
    this.password = password;
    }

    /**
    * 邮箱
    */
    private void setEmail(String email){
    this.email = email;
    }

    /**
    * 手机号
    */
    private void setMobile(String mobile){
    this.mobile = mobile;
    }

    /**
    * 状态  0：禁用   1：正常
    */
    private void setStatus(Integer status){
    this.status = status;
    }

    /**
    * 创建者ID
    */
    private void setCreateUserId(Long createUserId){
    this.createUserId = createUserId;
    }

    /**
    * 创建时间
    */
    private void setCreateTime(Date createTime){
    this.createTime = createTime;
    }

    /**
    * 用户所在的商城Id
    */
    private void setShopId(Long shopId){
    this.shopId = shopId;
    }


    /**
    * 
    */
    private Long getUserId(){
    return this.userId;
    }

    /**
    * 用户名
    */
    private String getUsername(){
    return this.username;
    }

    /**
    * 密码
    */
    private String getPassword(){
    return this.password;
    }

    /**
    * 邮箱
    */
    private String getEmail(){
    return this.email;
    }

    /**
    * 手机号
    */
    private String getMobile(){
    return this.mobile;
    }

    /**
    * 状态  0：禁用   1：正常
    */
    private Integer getStatus(){
    return this.status;
    }

    /**
    * 创建者ID
    */
    private Long getCreateUserId(){
    return this.createUserId;
    }

    /**
    * 创建时间
    */
    private Date getCreateTime(){
    return this.createTime;
    }

    /**
    * 用户所在的商城Id
    */
    private Long getShopId(){
    return this.shopId;
    }

}
