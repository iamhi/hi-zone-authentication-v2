package com.github.iamhi.hizone.authentication.v2.in.token.routes;

import com.github.iamhi.hizone.authentication.v2.in.token.actions.DecodeTokenInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.DecodeTokenRequest;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.DecodeTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class DecodeTokenRoute {

    private final RoutingDefaults<DecodeTokenResponse> routingDefaults;

    private final DecodeTokenInAction decodeTokenAction;

    @Bean
    RouterFunction<ServerResponse> decodeTokenRouteCompose() {
        return route(POST("/token/decode"), request -> request.bodyToMono(DecodeTokenRequest.class)
            .flatMap(decodeTokenAction)
            .contextWrite(context -> routingDefaults.userTokenConsumer().apply(context, request))
            .flatMap(routingDefaults.okResponseCreator())
            .onErrorResume(routingDefaults.requestExceptionHandler()));
    }
}




