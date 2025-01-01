package com.spring.admin.modules.sys.core.model.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthUtil {

    /**
     * 基础权限检查方法
     * @param authority 权限标识
     * @return 是否有权限
     */
    private boolean checkAuthority(String authority) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                log.debug("No authentication found");
                return false;
            }

            // 打印当前用户的所有权限，用于调试
            log.debug("Current user authorities: {}", authentication.getAuthorities());

            // 遍历所有权限进行匹配
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (grantedAuthority.getAuthority().equals(authority)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error checking authority {}: {}", authority, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 检查通用信息查看权限
     */
    public boolean hasGeneralInfoViewAuth() {
        return checkAuthority("general:query");
    }

    /**
     * 检查病例信息查看权限
     */
    public boolean hasCaseInfoViewAuth() {
        return checkAuthority("case:query");
    }

    /**
     * 检查手术信息查看权限
     */
    public boolean hasSurgicalInfoViewAuth() {
        return checkAuthority("surgical:query");
    }

    /**
     * 检查辅助治疗信息查看权限
     */
    public boolean hasAdjuvantInfoViewAuth() {
        return checkAuthority("adjuvant:query");
    }

    /**
     * 检查放疗信息查看权限
     */
    public boolean hasRadiationInfoViewAuth() {
        return checkAuthority("radiation:query");
    }

    /**
     * 检查内分泌信息查看权限
     */
    public boolean hasEndocrineInfoViewAuth() {
        return checkAuthority("endocrine:query");
    }

    /**
     * 检查新辅助治疗信息查看权限
     */
    public boolean hasNeoadjuvantInfoViewAuth() {
        return checkAuthority("neoadjuvant:query");
    }

//    /**
//     * 检查是否有指定权限
//     * @param authority 权限标识
//     * @return 是否有权限
//     */
//    public boolean hasAuthority(String authority) {
//        return checkAuthority(authority);
//    }
}