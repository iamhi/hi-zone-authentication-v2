package com.github.iamhi.hizone.authentication.v2.in.token.routes;

import com.github.iamhi.hizone.authentication.v2.in.token.actions.InvalidateTokensInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.InvalidateTokensRequest;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.InvalidateTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class InvalidateTokensRoute {

    private final RoutingDefaults<InvalidateTokenResponse> routingDefaults;

    private final InvalidateTokensInAction invalidateTokensAction;

    @Bean
    RouterFunction<ServerResponse> invalidateTokensRouteCompose() {
        return route(POST("/token/invalidate"), request -> request.bodyToMono(InvalidateTokensRequest.class)
            .flatMap(invalidateTokensAction)
            .contextWrite(context -> routingDefaults.userTokenConsumer().apply(context, request))
            .flatMap(routingDefaults.okResponseCreator().andThen(routingDefaults.invalidateCookies()))
            .onErrorResume(routingDefaults.requestExceptionHandler()));
    }
}
