package com.spring.admin.security.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.spring.admin.base.R;
import com.spring.admin.base.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * token 权限不足
 *
 * 
 * @date 2023/2/3
 */
@Component
public class TokenAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
        int code = HttpStatus.FORBIDDEN.value();
        String msg = StrUtil.format("请求访问:{} ,没有访问权限!", request.getRequestURI());
        JakartaServletUtil.write(response, JsonUtil.DEFAULT.toJson(R.NG(code, msg)), MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
