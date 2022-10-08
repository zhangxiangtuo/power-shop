package com.zxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zhangxiangtuo
 * @date 2022/10/7 15:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true)
/**
 *  将实体类中的属性为null的剔除出去
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("tbl_activity")
public class Activity {
    @TableId(type = IdType.ASSIGN_ID, value = "id")
    private String id;

    @TableField("owner")
    private String owner;

    @TableField("name")
    private String name;

    @TableField("start_date")
    private String startDate;

    @TableField("end_date")
    private String endDate;

    @TableField("cost")
    private String cost;

    @TableField("description")
    private String description;

    @TableField("create_time")
    private String createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("edit_time")
    private String editTime;

    @TableField("edit_by")
    private String editBy;
}
