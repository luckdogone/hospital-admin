package com.spring.admin.security.token;

import com.spring.admin.security.model.OnlineUser;
import com.spring.admin.security.model.UserInfo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * 
 * @date 2023/2/3
 */
public interface TokenProvider {
    /**
     * 创建token
     *
     * @param userInfo .
     * @return token
     */
    String createToken(@Nonnull UserInfo userInfo);

    /**
     * 创建token
     *
     * @param userInfo .
     * @param request  .
     * @return token
     */
    String createToken(@Nonnull UserInfo userInfo, @Nullable HttpServletRequest request);

    /**
     * 获取在线用户
     *
     * @param token .
     * @return .
     */
    Optional<OnlineUser> getOnlineFromToken(@Nonnull String token);

    /**
     * 是否延长token时间
     *
     * @param token .
     * @return .
     */
    boolean checkRenewal(@Nonnull String token);

    /**
     * 移除token
     *
     * @param token .
     * @return .
     */
    boolean removeToken(String token);
}
