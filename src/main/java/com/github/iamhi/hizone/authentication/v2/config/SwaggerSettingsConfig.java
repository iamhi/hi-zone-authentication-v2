package com.github.iamhi.hizone.authentication.v2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Hi-Zone Authentication-v2 api", version = "v1"),
    servers = {
        @Server(
            url = "https://api.ibeenhi.com/hi-zone-api/authentication-v2",
            description = "Https variant"
        ),
        @Server(
            url = "http://api.ibeenhi.com/hi-zone-api/authentication-v2",
            description = "Http variant"
        )
    })
@SecurityScheme(
    name = SwaggerSettingsConfig.AUTHENTICATION_SCHEME_NAME,
    type = SecuritySchemeType.HTTP,
    scheme = "bearer"
)
public class SwaggerSettingsConfig {
    public static final String AUTHENTICATION_SCHEME_NAME = "hi-zone-authentication-v2";
}
