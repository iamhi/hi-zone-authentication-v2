package com.github.iamhi.hizone.authentication.v2.in.token.actions;

import com.github.iamhi.hizone.authentication.v2.in.token.requests.DecodeTokenRequest;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.DecodeTokenResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface DecodeTokenInAction extends Function<DecodeTokenRequest, Mono<DecodeTokenResponse>> {
}
