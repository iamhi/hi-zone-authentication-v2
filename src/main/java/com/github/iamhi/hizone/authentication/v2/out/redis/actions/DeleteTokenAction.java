package com.github.iamhi.hizone.authentication.v2.out.redis.actions;

import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface DeleteTokenAction extends Function<String, Mono<Boolean>> {
}
