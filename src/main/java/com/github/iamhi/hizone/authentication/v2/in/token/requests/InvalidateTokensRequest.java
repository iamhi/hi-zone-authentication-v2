package com.github.iamhi.hizone.authentication.v2.in.token.requests;

public record InvalidateTokensRequest(
    String accessToken,
    String refreshToken
) {
}
