package com.github.iamhi.hizone.authentication.v2.in.user.routes;

import com.github.iamhi.hizone.authentication.v2.in.shared.CookiesResponseInput;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.AddRoleAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.AddRoleRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.AddRoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class AddRoleRoute {

    private final RoutingDefaults<AddRoleResponse> routingDefaults;

    private final AddRoleAction addRoleAction;

    @Bean
    RouterFunction<ServerResponse> addRoleRouteCompose() {
        return route(POST("/user/role"), request -> request.bodyToMono(AddRoleRequest.class)
            .flatMap(addRoleAction)
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
