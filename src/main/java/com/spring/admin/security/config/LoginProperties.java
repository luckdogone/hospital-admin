package com.spring.admin.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @date 2023/2/5
 */
@ConfigurationProperties(prefix = "security.login")
@Configuration
@Data
public class LoginProperties {
    /**
     * 登录私钥
     */
    private String rsaPrivateKey;
}
