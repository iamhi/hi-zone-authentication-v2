package com.github.iamhi.hizone.authentication.v2.in.shared;

import com.github.iamhi.hizone.authentication.v2.config.CookiesConfig;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OkResponseWithCookiesCreator<T> implements Function<CookiesResponseInput<T>, Mono<ServerResponse>> {

    private final CookiesConfig cookiesConfig;

    @Override
    public Mono<ServerResponse> apply(CookiesResponseInput cookiesResponseInput) {
        return ServerResponse.ok()
            .cookies(cookieMultiMap -> cookieMultiMap.addAll(createTokens(cookiesResponseInput.getAccessToken(), cookiesResponseInput.getRefreshToken())))
            .bodyValue(cookiesResponseInput.getBody());
    }

    MultiValueMap<String, ResponseCookie> createTokens(String accessToken, String responseToken) {
        return new MultiValueMapAdapter<>(Stream.of(
            Map.entry(cookiesConfig.getAccessTokenCookieName(), List.of(createAccessTokenCookie(accessToken))),
            Map.entry(cookiesConfig.getRefreshTokenCookieName(), List.of(createRefreshTokenCookie(responseToken)))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from(cookiesConfig.getAccessTokenCookieName(), accessToken)
//            .domain(cookiesConfig.getDomain())
            .httpOnly(cookiesConfig.getHttpOnly())
            .maxAge(cookiesConfig.getAccessTokenCookieMaxAge())
            .path(cookiesConfig.getAccessTokenCookiePath())
            .build();
    }

    ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(cookiesConfig.getRefreshTokenCookieName(), refreshToken)
//            .domain(cookiesConfig.getDomain())
            .httpOnly(cookiesConfig.getHttpOnly())
            .maxAge(cookiesConfig.getRefreshTokenCookieMaxAge())
            .path(cookiesConfig.getRefreshTokenCookiePath())
            .build();
    }
}
