package com.github.iamhi.hizone.authentication.v2.core.adapters;

import com.github.iamhi.hizone.authentication.v2.core.actions.CreateAuthTokensAction;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.InvalidRefreshTokenThrowable;
import com.github.iamhi.hizone.authentication.v2.core.mappers.UserMapper;
import com.github.iamhi.hizone.authentication.v2.in.shared.UserTokenConsumer;
import com.github.iamhi.hizone.authentication.v2.in.token.actions.RefreshTokensInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.RefreshTokensRequest;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.RefreshTokensResponse;
import com.github.iamhi.hizone.authentication.v2.out.postgres.UserRepository;
import com.github.iamhi.hizone.authentication.v2.out.redis.actions.DeleteTokenAction;
import com.github.iamhi.hizone.authentication.v2.out.redis.actions.GetTokenDataAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
record RefreshTokensActionImpl(
    CreateAuthTokensAction<RefreshTokensResponse> createAuthTokensAction,
    GetTokenDataAction getTokenDataAction,
    UserRepository userRepository,
    UserMapper userMapper,
    DeleteTokenAction deleteTokenAction
) implements RefreshTokensInAction {

    @Override
    public Mono<RefreshTokensResponse> apply(RefreshTokensRequest refreshTokensRequest) {
        return getTokenDataAction.apply(refreshTokensRequest.refreshToken())
            .switchIfEmpty(Mono.error(InvalidRefreshTokenThrowable::new))
            .flatMap(username -> deleteTokenAction.apply(refreshTokensRequest.refreshToken())
                .then(Mono.deferContextual(contextView -> Mono.justOrEmpty(contextView.getOrDefault(UserTokenConsumer.USER_TOKEN_CONTEXT, "")))
                    .flatMap(deleteTokenAction)
                    .then(Mono.just(username)))
                .flatMap(userRepository::findByUsername)
                .map(userMapper::fromEntity)
                .flatMap(userDto -> createAuthTokensAction.apply(userDto, RefreshTokensResponse::new)));
    }
}