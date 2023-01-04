package com.github.iamhi.hizone.authentication.v2.in.user.routes;

import com.github.iamhi.hizone.authentication.v2.config.SwaggerSettingsConfig;
import com.github.iamhi.hizone.authentication.v2.in.shared.CookiesResponseInput;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.UserRegisterAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserLoginRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserRegisterRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.UserRegisterResponse;
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
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class RegisterUserRoute {

    private final RoutingDefaults<UserRegisterResponse> routingDefaults;

    private final UserRegisterAction userRegisterAction;

    @Bean
    @RouterOperations(
        @RouterOperation(
            method = RequestMethod.POST,
            operation = @Operation(
                description = "Register with username, password and email",
                operationId = "Register",
                tags = "User operations",
                requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = UserRegisterRequest.class)
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = HttpURLConnection.HTTP_OK + "",
                        content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRegisterResponse.class)
                        )
                    )
                }
            )
        )
    )
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
