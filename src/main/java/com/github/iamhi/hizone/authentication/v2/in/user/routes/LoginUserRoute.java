package com.github.iamhi.hizone.authentication.v2.in.user.routes;

import com.github.iamhi.hizone.authentication.v2.config.SwaggerSettingsConfig;
import com.github.iamhi.hizone.authentication.v2.in.shared.CookiesResponseInput;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.DecodeTokenRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.UserLoginAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserLoginRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.AddRoleResponse;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.UserLoginResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.HttpURLConnection;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class LoginUserRoute {

    private final RoutingDefaults<UserLoginResponse> routingDefaults;

    private final UserLoginAction userLoginAction;

    @Bean
    @RouterOperations(
        @RouterOperation(
            method = RequestMethod.POST,
            operation = @Operation(
                description = "Login using username and passowrd",
                operationId = "Login",
                tags = "User operations",
                requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = UserLoginRequest.class)
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = HttpURLConnection.HTTP_OK + "",
                        content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserLoginResponse.class)
                        )
                    )
                }
            )
        )
    )
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
