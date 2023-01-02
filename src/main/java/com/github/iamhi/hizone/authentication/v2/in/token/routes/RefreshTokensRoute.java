package com.github.iamhi.hizone.authentication.v2.in.token.routes;

import com.github.iamhi.hizone.authentication.v2.config.CookiesConfig;
import com.github.iamhi.hizone.authentication.v2.config.SwaggerSettingsConfig;
import com.github.iamhi.hizone.authentication.v2.in.shared.CookiesResponseInput;
import com.github.iamhi.hizone.authentication.v2.in.token.actions.RefreshTokensInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.RefreshTokensRequest;
import com.github.iamhi.hizone.authentication.v2.in.shared.RoutingDefaults;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.RefreshTokensResponse;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.HttpURLConnection;
import java.util.Optional;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class RefreshTokensRoute {

    private final RoutingDefaults<RefreshTokensResponse> routingDefaults;

    private final RefreshTokensInAction refreshTokensAction;

    private final CookiesConfig cookiesConfig;

    @Bean
    @RouterOperations(
        @RouterOperation(
            method = RequestMethod.POST,
            operation = @Operation(
                description = "Get new access token with refresh token",
                operationId = "Refresh tokens",
                tags = "Token operations",
                security = {@SecurityRequirement(name = SwaggerSettingsConfig.AUTHENTICATION_SCHEME_NAME)},
                requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = RefreshTokensRequest.class)
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = HttpURLConnection.HTTP_OK + "",
                        content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RefreshTokensResponse.class)
                        )
                    )
                }
            )
        )
    )
    RouterFunction<ServerResponse> refreshTokensRouteCompose() {
        return route(POST("/token/refresh"), request -> request.bodyToMono(RefreshTokensRequest.class)
            .map(refreshTokensRequest -> populateIfEmpty(request, refreshTokensRequest))
            .flatMap(refreshTokensAction)
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

    private RefreshTokensRequest populateIfEmpty(ServerRequest serverRequest, RefreshTokensRequest refreshTokensRequest) {
        return new RefreshTokensRequest(StringUtils.firstNonEmpty(
            refreshTokensRequest.refreshToken(),
            getRefreshTokenFromCookie(serverRequest)
        ));
    }

    private String getRefreshTokenFromCookie(ServerRequest serverRequest) {
        return Optional.ofNullable(serverRequest.cookies()
            .getFirst(cookiesConfig.getRefreshTokenCookieName())).map(HttpCookie::getValue).orElse("");
    }
}
