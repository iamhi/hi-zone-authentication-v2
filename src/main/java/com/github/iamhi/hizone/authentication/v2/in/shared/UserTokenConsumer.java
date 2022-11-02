package com.github.iamhi.hizone.authentication.v2.in.shared;

import com.github.iamhi.hizone.authentication.v2.config.CookiesConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.util.context.Context;

import java.util.Optional;
import java.util.function.BiFunction;

@Component
public record UserTokenConsumer(
    CookiesConfig cookiesConfig
) implements BiFunction<Context, ServerRequest, Context> {

    public static String USER_TOKEN_CONTEXT = "user-token";

    public static String ACCESS_TOKEN_QUERY_PARAM = "accessToken";

    @Override
    public Context apply(Context context, ServerRequest serverRequest) {
        return context.put(USER_TOKEN_CONTEXT, getAccessTokenFromServerRequest(serverRequest));
    }

    String getAccessTokenFromServerRequest(ServerRequest serverRequest) {
        return StringUtils.defaultString(StringUtils.firstNonEmpty(getAccessTokenFromCookies(serverRequest),
            getAccessTokenFromHeader(serverRequest),
            getAccessTokenFromQueryParameter(serverRequest)));
    }

    String getAccessTokenFromCookies(ServerRequest serverRequest) {
        return Optional.ofNullable(serverRequest.cookies().getFirst(cookiesConfig.getAccessTokenCookieName()))
            .map(HttpCookie::getValue).orElse("");
    }

    String getAccessTokenFromHeader(ServerRequest serverRequest) {
        return serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);
    }

    String getAccessTokenFromQueryParameter(ServerRequest serverRequest) {
        return serverRequest.queryParam(ACCESS_TOKEN_QUERY_PARAM).orElse("");
    }
}
