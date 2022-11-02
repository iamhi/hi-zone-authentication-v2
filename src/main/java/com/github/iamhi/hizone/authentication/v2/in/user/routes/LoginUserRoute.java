package com.github.iamhi.hizone.authentication.v2.in.user.routes;

import com.github.iamhi.hizone.authentication.v2.in.shared.CookiesResponseInput;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.UserLoginAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserLoginRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class LoginUserRoute {

    private final RoutingDefaults<UserLoginResponse> routingDefaults;

    private final UserLoginAction userLoginAction;

    @Bean
    RouterFunction<ServerResponse> loginUserRouteCompose() {
        return route(POST("/user/login"), request -> request.bodyToMono(UserLoginRequest.class)
            .flatMap(userLoginAction)
            .contextWrite(context -> routingDefaults.userTokenConsumer().apply(context, request))
            .flatMap(response -> routingDefaults.okResponseWithCookiesCreator().apply(
                new CookiesResponseInput<>(
                    response::accessToken,
                    response::refreshToken,
                    response
                )
            ))
            .onErrorResume(routingDefaults.requestExceptionHandler()));
    }
}
