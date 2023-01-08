package com.github.iamhi.hizone.authentication.v2.in.token.actions;

import com.github.iamhi.hizone.authentication.v2.in.token.responses.MeTokenResponse;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@FunctionalInterface
public interface MeTokenInAction extends Supplier<Mono<MeTokenResponse>> {
}
