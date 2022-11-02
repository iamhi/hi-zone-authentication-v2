package com.github.iamhi.hizone.authentication.v2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "authentication.service.token")
@Data
public class TokenConfig {

    long accessTokenLife;

    long refreshTokenLife;
}
