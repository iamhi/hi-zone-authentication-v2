package com.github.iamhi.hizone.authentication.v2.in.token.routes;

import com.github.iamhi.hizone.authentication.v2.config.SwaggerSettingsConfig;
import com.github.iamhi.hizone.authentication.v2.in.token.actions.InvalidateTokensInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.InvalidateTokensRequest;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.InvalidateTokensResponse;
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
public class InvalidateTokensRoute {

    private final RoutingDefaults<InvalidateTokensResponse> routingDefaults;

    private final InvalidateTokensInAction invalidateTokensAction;

    @Bean
    @RouterOperations(
        @RouterOperation(
            method = RequestMethod.POST,
            operation = @Operation(
                description = "Invalidate a token",
                operationId = "Invalidate",
                tags = "Token operations",
                security = {@SecurityRequirement(name = SwaggerSettingsConfig.AUTHENTICATION_SCHEME_NAME)},
                requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = InvalidateTokensRequest.class)
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = HttpURLConnection.HTTP_OK + "",
                        content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InvalidateTokensResponse.class)
                        )
                    )
                }
            )
        )
    )
    RouterFunction<ServerResponse> invalidateTokensRouteCompose() {
        return route(POST("/token/invalidate"), request -> request.bodyToMono(InvalidateTokensRequest.class)
            .flatMap(invalidateTokensAction)
            .contextWrite(context -> routingDefaults.userTokenConsumer().apply(context, request))
            .flatMap(routingDefaults.okResponseCreator().andThen(routingDefaults.invalidateCookies()))
            .onErrorResume(routingDefaults.requestExceptionHandler()));
    }
}
