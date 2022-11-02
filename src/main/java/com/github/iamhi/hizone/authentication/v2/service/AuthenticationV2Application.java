package com.github.iamhi.hizone.authentication.v2.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = {"com.github.iamhi.hizone.authentication.v2"})
@ConfigurationPropertiesScan("com.github.iamhi.hizone.authentication.v2.config")
@EnableR2dbcRepositories(basePackages = {"com.github.iamhi.hizone.authentication.v2.out.postgres"})
public class AuthenticationV2Application {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationV2Application.class, args);
    }
}
