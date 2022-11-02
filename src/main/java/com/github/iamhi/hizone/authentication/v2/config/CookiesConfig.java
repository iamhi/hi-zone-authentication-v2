package com.github.iamhi.hizone.authentication.v2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "authentication.service.cookies")
@Data
public class CookiesConfig {

    String accessTokenCookieName;

    Long accessTokenCookieMaxAge;

    String accessTokenCookiePath;

    String refreshTokenCookieName;

    Long refreshTokenCookieMaxAge;

    String refreshTokenCookiePath;

    Boolean httpOnly;

    String domain;
}