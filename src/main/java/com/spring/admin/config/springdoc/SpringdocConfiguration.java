package com.spring.admin.config.springdoc;

import com.spring.admin.base.BootAdminConst;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @date 2023/1/12
 */
@Configuration
public class SpringdocConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getInfo()
                ).addSecurityItem(
                        new io.swagger.v3.oas.models.security.SecurityRequirement()
                                .addList(BootAdminConst.X_ACCESS_TOKEN)
                ).schemaRequirement(
                        BootAdminConst.X_ACCESS_TOKEN,
                        securityScheme()
                ).components(
                        new Components()
                                .addSecuritySchemes(BootAdminConst.X_ACCESS_TOKEN, securityScheme())
                );
    }

    private Info getInfo() {
        return new Info()
                .title("hospital-admin系统API")
                .contact(
                        new Contact()
                                .name("luckdog")
                                .email("luckdogone@hotmail.com")
                )
                .version("v5.0.0")
                ;
    }

    private SecurityScheme securityScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.APIKEY);
        securityScheme.setName(BootAdminConst.X_ACCESS_TOKEN);
        securityScheme.setIn(SecurityScheme.In.HEADER);
        return securityScheme;
    }
}
