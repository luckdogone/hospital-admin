package com.spring.admin.security.token;

import com.spring.admin.security.model.UserInfo;

import java.util.List;
import java.util.Optional;

/**
 * 登录用户缓存
 *
 * 
 * @date 2023/2/4
 */
public interface UserCacheProvider {
    /**
     * 缓存登录用户
     *
     * @param key      key/username
     * @param userInfo 登录用户信息
     * @return .
     */
    boolean cacheUser(String key, UserInfo userInfo);

    /**
     * 获取所有缓存的登录用户
     *
     * @return .
     */
    Optional<List<UserInfo>> getCacheUsers();

    /**
     * 清理登录用户
     *
     * @param key key/username
     * @return .
     */
    boolean clearUser(String key);

    /**
     * 获取登录用户
     *
     * @param key key/username
     * @return .
     */
    Optional<UserInfo> getUser(String key);
}
