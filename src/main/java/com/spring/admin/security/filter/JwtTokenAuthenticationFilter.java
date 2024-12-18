package com.spring.admin.security.filter;

import cn.hutool.core.util.StrUtil;
import com.spring.admin.base.util.JwtUtil;
import com.spring.admin.security.model.OnlineUser;
import com.spring.admin.security.service.UserDetailServiceImpl;
import com.spring.admin.security.token.JwtTokenRedisCacheProvider;
import com.spring.admin.security.token.UserCacheProvider;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * token认证过滤器
 *
 * 
 * @date 2023/1/30
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenRedisCacheProvider jwtTokenRedisCacheProvider;
    private final UserDetailServiceImpl userDetailService;
    @Lazy
    @Resource
    private UserCacheProvider userCacheProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String _jwtToken = JwtUtil.getTokenByRequest(request);
        if (StrUtil.isNotBlank(_jwtToken)) {
            Optional<OnlineUser> online = jwtTokenRedisCacheProvider.getOnlineFromToken(_jwtToken);
            if (online.isPresent()) {
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(JwtUtil.getUsername(_jwtToken));

                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails,
                            userDetails.getAuthorities()
                        );
                    authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } else {
                userCacheProvider.clearUser(JwtUtil.getUsername(_jwtToken));
            }
            jwtTokenRedisCacheProvider.checkRenewal(_jwtToken);
        }
        filterChain.doFilter(request, response);
    }
}
