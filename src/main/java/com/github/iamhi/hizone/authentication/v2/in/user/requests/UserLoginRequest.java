package com.github.iamhi.hizone.authentication.v2.in.user.requests;

public record UserLoginRequest(
    String username,
    String password
) {
}
