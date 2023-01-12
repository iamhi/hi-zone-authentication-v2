package com.github.iamhi.hizone.authentication.v2.in.shared;

import com.github.iamhi.hizone.authentication.v2.config.CookiesConfig;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public record InvalidateCookies(
    CookiesConfig cookiesConfig
) implements Function<Mono<ServerResponse>, Mono<ServerResponse>> {

    @Override
    public Mono<ServerResponse> apply(Mono<ServerResponse> monoServerResponse) {
        return monoServerResponse.map(
            serverResponse -> {
                serverResponse.cookies().remove(cookiesConfig.getAccessTokenCookieName());
                serverResponse.cookies().remove(cookiesConfig.getRefreshTokenCookieName());
                serverResponse.cookies().addAll(createTokens());

                return serverResponse;
            }
        );
    }

    MultiValueMap<String, ResponseCookie> createTokens() {
        return new MultiValueMapAdapter<>(Stream.of(
            Map.entry(cookiesConfig.getAccessTokenCookieName(), List.of(createAccessTokenCookie())),
            Map.entry(cookiesConfig.getRefreshTokenCookieName(), List.of(createRefreshTokenCookie()))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    ResponseCookie createAccessTokenCookie() {
        return ResponseCookie.from(cookiesConfig.getAccessTokenCookieName(), "")
            .domain(cookiesConfig.getDomain())
            .httpOnly(cookiesConfig.getHttpOnly())
            .secure(cookiesConfig.getUseSecure())
            .maxAge(0)
            .path(cookiesConfig.getAccessTokenCookiePath())
            .build();
    }

    ResponseCookie createRefreshTokenCookie() {
        return ResponseCookie.from(cookiesConfig.getRefreshTokenCookieName(), "")
            .domain(cookiesConfig.getDomain())
            .httpOnly(cookiesConfig.getHttpOnly())
            .secure(cookiesConfig.getUseSecure())
            .maxAge(0)
            .path(cookiesConfig.getRefreshTokenCookiePath())
            .build();
    }
}
