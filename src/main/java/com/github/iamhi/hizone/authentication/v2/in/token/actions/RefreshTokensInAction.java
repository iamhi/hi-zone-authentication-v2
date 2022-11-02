package com.github.iamhi.hizone.authentication.v2.in.token.actions;

import com.github.iamhi.hizone.authentication.v2.in.token.requests.RefreshTokensRequest;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.RefreshTokensResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface RefreshTokensInAction extends Function<RefreshTokensRequest, Mono<RefreshTokensResponse>> {
}
