package com.spring.admin.base.util;

import com.spring.admin.base.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class PasswordUtilTest {

    @Test
    void encoder() {
        String password = PasswordUtil.encoder("123456");
        log.info(password);
    }
}
