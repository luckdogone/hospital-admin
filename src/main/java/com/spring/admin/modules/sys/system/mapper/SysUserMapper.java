package com.spring.admin.modules.sys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.system.model.entity.SysUser;
import com.spring.admin.modules.sys.system.model.query.UserQuery;
import com.spring.admin.modules.sys.system.model.vo.UserVO;
import jakarta.annotation.Nullable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 
 * @date 2023/2/4
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名获取用户信息
     *
     * @param username .
     * @return .
     */
    SysUser getByUsername(@Param("username") String username);

    /**
     * 根据用户名获取已授权的角色代码
     *
     * @param username 用户名
     * @return 已授权的角色代码
     */
    @Nullable
    Set<String> getRoleCodeByUsername(@Param("username") String username);

    /**
     * 根据用户ID 获取对应的角色ID集合
     *
     * @param userId 用户ID
     * @return 角色ID集合
     */
    @Nullable
    Set<String> queryUserRole(@Param("userId") String userId);

    /**
     * 获取全部角色code
     *
     * @return .
     */
    @Nullable
    Set<String> allRoleCode();
    /*=========================================================*/

    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<UserVO> queryPage(Page<UserVO> page, @Param("query") UserQuery query);

    /**
     * 保存用户角色
     *
     * @param userId  .
     * @param roleIds .
     * @return .
     */
    int saveUserRole(@Param("userId") String userId, @Param("roleIds") Set<String> roleIds);

    /**
     * 根据用户ID删除用户角色
     *
     * @param userId .
     * @return .
     */
    int delUserRoleByUserid(@Param("userId") String userId);

    /**
     * 根据用户ID删除用户角色
     *
     * @param userIds .
     * @return .
     */
    int delUserRoleByUserids(@Param("userIds") List<String> userIds);

    /**
     * 重置密码
     *
     * @param username .
     * @param password .
     * @return .
     */
    int resetPassword(@Param("username") String username, @Param("password") String password);
}
