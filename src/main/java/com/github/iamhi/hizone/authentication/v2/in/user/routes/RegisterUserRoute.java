package com.github.iamhi.hizone.authentication.v2.in.user.routes;

import com.github.iamhi.hizone.authentication.v2.in.shared.CookiesResponseInput;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.UserRegisterAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserRegisterRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.UserRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class RegisterUserRoute {

    private final RoutingDefaults<UserRegisterResponse> routingDefaults;

    private final UserRegisterAction userRegisterAction;

    @Bean
    RouterFunction<ServerResponse> registerUserRouteCompose() {
        return route(POST("/user/register").and(accept(MediaType.APPLICATION_JSON)), request -> request.bodyToMono(UserRegisterRequest.class)
            .flatMap(userRegisterAction)
            .contextWrite(context -> routingDefaults.userTokenConsumer().apply(context, request))
            .flatMap(response -> routingDefaults.okResponseWithCookiesCreator().apply(new CookiesResponseInput<>(
                response::accessToken,
                response::refreshToken,
                response
            )))
            .onErrorResume(routingDefaults.requestExceptionHandler()));
    }
}
