package com.github.iamhi.hizone.authentication.v2.core.adapters;

import com.github.iamhi.hizone.authentication.v2.core.actions.ComparePasswordsAction;
import com.github.iamhi.hizone.authentication.v2.core.actions.CreateAuthTokensAction;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.InvalidLoginThrowable;
import com.github.iamhi.hizone.authentication.v2.core.mappers.UserMapper;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.UserLoginAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserLoginRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.UserLoginResponse;
import com.github.iamhi.hizone.authentication.v2.out.postgres.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
record UserLoginActionImpl(
    UserRepository userRepository,
    ComparePasswordsAction comparePasswordsAction,
    UserMapper userMapper,
    CreateAuthTokensAction<UserLoginResponse> createAuthTokensAction
) implements UserLoginAction {

    @Override
    public Mono<UserLoginResponse> apply(UserLoginRequest userLoginRequest) {
        return userRepository.findByUsername(userLoginRequest.username())
            .switchIfEmpty(Mono.error(InvalidLoginThrowable::new))
            .flatMap(userEntity -> Boolean.TRUE.equals(comparePasswordsAction.apply(userLoginRequest.password(), userEntity.getPassword())) ?
                Mono.just(userMapper.fromEntity(userEntity))
                : Mono.error(InvalidLoginThrowable::new))
            .flatMap(userDto -> createAuthTokensAction.apply(userDto, (accessToken, responseToken) -> new UserLoginResponse(
                userDto.uuid(),
                userDto.username(),
                accessToken,
                responseToken
            )));
    }
}
