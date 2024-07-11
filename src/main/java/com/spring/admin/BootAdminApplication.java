package com.spring.admin;

import cn.hutool.extra.spring.EnableSpringUtil;
import com.spring.admin.base.BootAdminApp;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 
 * @date 2023/1/11
 */
@SpringBootApplication
@EnableSpringUtil
@EnableAsync
public class BootAdminApplication extends BootAdminApp {
    public static void main(String[] args) {
        bootstrap(BootAdminApplication.class, args);
    }

    @Override
    protected void doSomethingAfterBooted() {

    }
}
