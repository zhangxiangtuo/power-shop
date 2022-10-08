package com.zxt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 13:29
 */
/*
    公共的返回值结果集
 */
@Data //通过该注解实现了当前实体类的toString,hash...get...set...方法
/*
    创建对象
        R r = new R();
        r.setCode(...);
        r.setMsg(...);
        ...

    通过构建者模式来封装对象
        R.builder()
         .code(...).msg(...).success(...).data(...)
         .build();
 */
@Builder //通过构建者的模式进行创建实体类对象，期间可以省略封装的过程
//@Getter
//@Setter
@NoArgsConstructor // 生成无参构造
@AllArgsConstructor // 生成全参构造
/*
    通过链式方式来创建对象
        R r = new R().setCode(...).setMsg(...).setSuccess(...).setData(...);
 */
@Accessors(chain = true)
public class R <T> implements Serializable {
    private Integer code;
    private String  msg;
    private Boolean success;
    private T data;
}
