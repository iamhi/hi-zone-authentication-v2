package com.github.iamhi.hizone.authentication.v2.out.redis.actions;

import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

public interface ExtendTokenAction extends BiFunction<String, Long, Mono<String>> {
}
