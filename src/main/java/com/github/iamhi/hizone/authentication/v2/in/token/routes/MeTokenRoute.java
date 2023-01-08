package com.github.iamhi.hizone.authentication.v2.in.token.routes;

import com.github.iamhi.hizone.authentication.v2.config.SwaggerSettingsConfig;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.token.actions.DecodeTokenInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.actions.MeTokenInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.DecodeTokenRequest;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.DecodeTokenResponse;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.MeTokenResponse;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.RefreshTokensResponse;
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
public class MeTokenRoute {

    private final RoutingDefaults<MeTokenResponse> routingDefaults;

    private final MeTokenInAction meTokenInAction;

    @Bean
    @RouterOperations(
        @RouterOperation(
            method = RequestMethod.POST,
            operation = @Operation(
                description = "Get user details from cookie access token",
                operationId = "Me",
                tags = "Token operations",
                security = {@SecurityRequirement(name = SwaggerSettingsConfig.AUTHENTICATION_SCHEME_NAME)},
                responses = {
                    @ApiResponse(
                        responseCode = HttpURLConnection.HTTP_OK + "",
                        content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MeTokenResponse.class)
                        )
                    )
                }
            )
        )
    )
    RouterFunction<ServerResponse> meTokenRouteCompose() {
        return route(POST("/token/me"), request -> meTokenInAction.get()
            .contextWrite(context -> routingDefaults.userTokenConsumer().apply(context, request))
            .flatMap(routingDefaults.okResponseCreator())
            .onErrorResume(routingDefaults.requestExceptionHandler()));
    }

}
