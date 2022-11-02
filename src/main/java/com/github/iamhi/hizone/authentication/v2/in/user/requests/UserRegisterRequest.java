package com.github.iamhi.hizone.authentication.v2.in.user.requests;

public record UserRegisterRequest(
    String username,
    String password,
    String email
) {
}
