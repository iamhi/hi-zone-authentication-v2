package com.github.iamhi.hizone.authentication.v2.out.redis.actions;

import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface GetTokenDataAction extends Function<String, Mono<String>> {
}
