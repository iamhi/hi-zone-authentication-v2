package com.github.iamhi.hizone.authentication.v2.in.shared;

import java.util.function.Supplier;

public record CookiesResponseInput<T>(
    Supplier<String> accessToken,
    Supplier<String> refreshToken,
    T body
) {

    String getAccessToken() {
        return accessToken.get();
    }

    String getRefreshToken() {
        return refreshToken.get();
    }

    T getBody() {
        return body;
    }
}
