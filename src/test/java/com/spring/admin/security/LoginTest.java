package com.spring.admin.security;

import com.spring.admin.base.util.AesEncryptUtil;
import com.spring.admin.base.util.JsonUtil;
import com.spring.admin.modules.sys.auth.model.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 
 * @date 2023/2/4
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class LoginTest {
    @Test
    @DisplayName("测试登录")
    void login(@Autowired MockMvc mockMvc) throws Exception {
        LoginRequest loginBody = new LoginRequest();
        String password = AesEncryptUtil.encrypt("123456");
        loginBody.setUsername("admin");
        loginBody.setPassword(password);
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/auth/login")
                .content(JsonUtil.DEFAULT.toJson(loginBody))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)

        ).andReturn();
        String body = result.getResponse().getContentAsString();
        log.info("login response: {}", body);
    }

}
