package com.github.iamhi.hizone.authentication.v2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "authentication.service.user.role.secrets")
@Data
public class UserRoleSecretsConfig {

    String service;

    String admin;
}
