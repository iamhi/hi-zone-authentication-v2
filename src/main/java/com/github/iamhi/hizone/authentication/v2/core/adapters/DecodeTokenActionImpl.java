package com.github.iamhi.hizone.authentication.v2.core.adapters;

import com.github.iamhi.hizone.authentication.v2.core.dto.UserRoleConstants;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.InvalidAuthorizationTokenThrowable;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.NoPermissionsThrowable;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.UserNotFoundThrowable;
import com.github.iamhi.hizone.authentication.v2.core.mappers.UserMapper;
import com.github.iamhi.hizone.authentication.v2.in.shared.UserTokenConsumer;
import com.github.iamhi.hizone.authentication.v2.in.token.actions.DecodeTokenInAction;
import com.github.iamhi.hizone.authentication.v2.in.token.requests.DecodeTokenRequest;
import com.github.iamhi.hizone.authentication.v2.in.token.responses.DecodeTokenResponse;
import com.github.iamhi.hizone.authentication.v2.out.redis.actions.GetTokenDataAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
record DecodeTokenActionImpl(
    GetTokenDataAction getTokenDataAction,
    UserMapper userMapper
) implements DecodeTokenInAction {

    @Override
    public Mono<DecodeTokenResponse> apply(DecodeTokenRequest decodeTokenRequest) {
        return Mono.deferContextual(contextView -> Mono.justOrEmpty(contextView.getOrDefault(UserTokenConsumer.USER_TOKEN_CONTEXT, "")))
            .flatMap(getTokenDataAction)
            .map(userMapper::fromString)
            .switchIfEmpty(Mono.error(InvalidAuthorizationTokenThrowable::new))
            .flatMap(userDto -> userDto.roles().contains(UserRoleConstants.SERVICE_ROLE)
                ? getTokenDataAction.apply(decodeTokenRequest.token())
                .map(userMapper::fromString)
                .map(decodedUserDto -> new DecodeTokenResponse(
                    decodedUserDto.uuid(),
                    decodedUserDto.username(),
                    decodedUserDto.roles()
                ))
                .switchIfEmpty(Mono.error(UserNotFoundThrowable::new))
                : Mono.error(NoPermissionsThrowable::new));
    }
}
