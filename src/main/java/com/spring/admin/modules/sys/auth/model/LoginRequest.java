package com.spring.admin.modules.sys.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * login request body
 *
 * 
 * @date 2023/1/30
 */
@Data
@EqualsAndHashCode
@ToString
public class LoginRequest implements Serializable {
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名为空")
    private String username;
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码为空")
    private String password;
}
