package com.github.iamhi.hizone.authentication.v2.in.status;

import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class StatusRoute {

    private static final String ROUTER_PREFIX = "/status";

    @RouterOperations(
        @RouterOperation(method = RequestMethod.GET, operation = @Operation(
            description = "Get a ping",
            operationId = "Ping", tags = "what is a tag?"
        ))
    )
    @Bean
    public RouterFunction<ServerResponse> composePingRoute() {
        return route(GET(ROUTER_PREFIX + "/ping"), serverRequest ->
            ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(Map.of("ping", "Pong from authentication")), Map.class));
    }
}
