package com.spring.admin.modules.sys.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户部门表
 *
 * 
 * @date 2023/6/8
 */
@Data
@TableName("sys_user_depart")
public class SysUserDepart implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    protected String id;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 部门id
     */
    private String depId;


}
