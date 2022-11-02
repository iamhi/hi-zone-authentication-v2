package com.github.iamhi.hizone.authentication.v2.in.user.responses;

public record UserLoginResponse(
    String uuid,
    String username,
    String accessToken,
    String refreshToken
) {
}
