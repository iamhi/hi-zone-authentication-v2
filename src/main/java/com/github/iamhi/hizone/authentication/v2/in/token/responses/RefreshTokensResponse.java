package com.github.iamhi.hizone.authentication.v2.in.token.responses;

public record RefreshTokensResponse(
    String accessToken,
    String refreshToken
) {
}
