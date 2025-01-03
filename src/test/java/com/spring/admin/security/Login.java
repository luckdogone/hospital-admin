package com.spring.admin.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spring.admin.base.R;
import com.spring.admin.base.util.AesEncryptUtil;
import com.spring.admin.base.util.JsonUtil;
import com.spring.admin.modules.sys.auth.model.LoginRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

/**
 * 
 * @date 2023/2/4
 */
public class Login {
  protected   String login(MockMvc mockMvc) throws Exception {
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
        R<Map<String, Object>> res = JsonUtil.DEFAULT.fromJson(
            body,
            new TypeReference<R<Map<String, Object>>>() {
            }
        );
        if (res.isSuccess()) {
            return res.getResult().get("token").toString();
        }
        throw new RuntimeException(res.getMessage());
    }
}
