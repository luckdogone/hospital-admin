package com.spring.admin.modules.sys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.admin.modules.sys.system.model.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限
 *
 * 
 * @date 2023/3/2
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    /**
     * 根据权限ID获取所有的父级ID
     *
     * @param permissionIds .
     * @return .
     */
    List<String> getParentIdByPermissionIds(@Param("permissionIds") List<String> permissionIds);

    /**
     * 根据角色ID查询权限ID,只查询没有下级的权限
     *
     * @param roleId .
     * @return .
     */
    List<String> getPermissionIdByRoleId(@Param("roleId") String roleId);
}
