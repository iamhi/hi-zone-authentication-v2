package com.github.iamhi.hizone.authentication.v2.in.token.actions;

import com.github.iamhi.hizone.authentication.v2.in.token.requests.InvalidateTokensRequest;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.InvalidateTokenResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface InvalidateTokensInAction extends Function<InvalidateTokensRequest, Mono<InvalidateTokenResponse>> {
}
