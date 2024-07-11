package com.spring.admin.modules.sys.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色权限表
 *
 * 
 * @date 2023/2/4
 */
@Data
@EqualsAndHashCode
@ToString
public class SysRolePermission implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    protected String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String permissionId;

    /**
     * 数据权限
     */
    private String dataRuleIds;
    /**
     * 创建时间
     */
    private LocalDateTime created;
    /**
     * 创建人
     */
    private String createdBy;

}
