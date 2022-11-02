package com.github.iamhi.hizone.authentication.v2.in.shared;

import org.springframework.stereotype.Component;

@Component
public record RoutingDefaults<R>(
    UserTokenConsumer userTokenConsumer,
    RequestExceptionHandler requestExceptionHandler,
    OkResponseCreator<R> okResponseCreator,
    OkResponseWithCookiesCreator<R> okResponseWithCookiesCreator,
    InvalidateCookies invalidateCookies
) {
}
