package com.github.iamhi.hizone.authentication.v2.core.actions;

import com.github.iamhi.hizone.authentication.v2.config.TokenConfig;
import com.github.iamhi.hizone.authentication.v2.core.dto.UserDto;
import com.github.iamhi.hizone.authentication.v2.core.mappers.UserMapper;
import com.github.iamhi.hizone.authentication.v2.out.redis.actions.CreateTokenAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Component
record CreateAuthTokensActionImpl<T>(
    CreateTokenAction createTokenAction,
    UserMapper userMapper,
    TokenConfig tokenConfig
) implements CreateAuthTokensAction<T> {
    @Override
    public Mono<T> apply(UserDto userDto, BiFunction<String, String, T> tokensConsumer) {
        return createTokenAction.apply(userMapper.toString(userDto), tokenConfig.getAccessTokenLife())
            .flatMap(accessToken -> createTokenAction.apply(userDto.username(), tokenConfig.getRefreshTokenLife())
                .map(refreshToken -> tokensConsumer.apply(accessToken, refreshToken)));
    }
}
