package com.github.iamhi.hizone.authentication.v2.core.adapters;

import com.github.iamhi.hizone.authentication.v2.in.token.actions.InvalidateTokensInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.InvalidateTokensRequest;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.InvalidateTokensResponse;
import com.github.iamhi.hizone.authentication.v2.out.redis.actions.DeleteTokenAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
record InvalidateTokensActionImpl(
    DeleteTokenAction deleteTokenAction
) implements InvalidateTokensInAction {

    @Override
    public Mono<InvalidateTokensResponse> apply(InvalidateTokensRequest invalidateTokensRequest) {
        return deleteTokenAction.apply(invalidateTokensRequest.accessToken()).then(deleteTokenAction.apply(invalidateTokensRequest.refreshToken()))
            .map(deleted -> new InvalidateTokensResponse());
    }
}
