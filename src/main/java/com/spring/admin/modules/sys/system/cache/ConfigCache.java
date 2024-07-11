package com.spring.admin.modules.sys.system.cache;

import com.spring.admin.config.cache.BootAdminCache;
import com.spring.admin.config.cache.CacheUtil;
import com.spring.admin.config.cache.DefaultKeyValue;
import com.spring.admin.modules.sys.system.model.entity.SysConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 
 * @date 2023/6/12
 */
@Component
@RequiredArgsConstructor
public class ConfigCache implements CacheUtil {
    private final BootAdminCache cache;

    /**
     * 设置缓存
     *
     * @param config .
     * @return .
     */
    public boolean putCache(SysConfig config) {
        String key = getCacheKey(DefaultKeyValue.SYS_CONFIG, config.getCode());
        return cache.set(key, config.getValue());
    }

    /**
     * 移除缓存
     *
     * @param code .
     * @return .
     */
    public boolean delCache(String code) {
        String key = getCacheKey(DefaultKeyValue.SYS_CONFIG, code);
        return cache.del(key);
    }

    /**
     * 获取缓存
     *
     * @param code .
     * @return .
     */
    public Optional<String> getCache(String code) {
        String key = getCacheKey(DefaultKeyValue.SYS_CONFIG, code);
        return cache.getStr(key);
    }
}
