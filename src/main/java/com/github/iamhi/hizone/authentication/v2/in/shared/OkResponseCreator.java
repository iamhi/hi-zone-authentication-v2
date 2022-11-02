package com.github.iamhi.hizone.authentication.v2.in.shared;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class OkResponseCreator<T> implements Function<T, Mono<ServerResponse>> {

    @Override
    public Mono<ServerResponse> apply(T t) {
        return ServerResponse.ok().bodyValue(t);
    }
}
