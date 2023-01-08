package com.github.iamhi.hizone.authentication.v2.core.adapters;

import com.github.iamhi.hizone.authentication.v2.core.exceptions.InvalidAuthorizationTokenThrowable;
import com.github.iamhi.hizone.authentication.v2.core.mappers.UserMapper;
import com.github.iamhi.hizone.authentication.v2.in.shared.UserTokenConsumer;
import com.github.iamhi.hizone.authentication.v2.in.token.actions.MeTokenInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.MeTokenResponse;
import com.github.iamhi.hizone.authentication.v2.out.redis.actions.GetTokenDataAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public record MeTokenActionImpl(
    GetTokenDataAction getTokenDataAction,
    UserMapper userMapper
) implements MeTokenInAction {

    @Override
    public Mono<MeTokenResponse> get() {
        return Mono.deferContextual(contextView -> Mono.justOrEmpty(contextView.getOrDefault(UserTokenConsumer.USER_TOKEN_CONTEXT, ""))
            .flatMap(getTokenDataAction)
            .map(userMapper::fromString)
            .switchIfEmpty(Mono.error(InvalidAuthorizationTokenThrowable::new))
            .map(userDto -> new MeTokenResponse(
                userDto.uuid(),
                userDto.username(),
                userDto.roles()
            )));
    }
}
