package com.github.iamhi.hizone.authentication.v2.core.adapters;

import com.github.iamhi.hizone.authentication.v2.core.actions.CreateAuthTokensAction;
import com.github.iamhi.hizone.authentication.v2.core.actions.EncryptPasswordAction;
import com.github.iamhi.hizone.authentication.v2.core.dto.UserRoleConstants;
import com.github.iamhi.hizone.authentication.v2.core.mappers.UserMapper;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.UserRegisterAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserRegisterRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.UserRegisterResponse;
import com.github.iamhi.hizone.authentication.v2.out.postgres.UserRepository;
import com.github.iamhi.hizone.authentication.v2.out.postgres.models.UserEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Component
record UserRegisterActionImpl(
    UserRepository userRepository,
    EncryptPasswordAction encryptPasswordAction,
    UserMapper userMapper,
    CreateAuthTokensAction<UserRegisterResponse> createAuthTokensAction
) implements UserRegisterAction {

    @Override
    public Mono<UserRegisterResponse> apply(UserRegisterRequest userRegisterRequest) {
        return userRepository.save(
                new UserEntity(
                    UUID.randomUUID().toString(),
                    userRegisterRequest.username(),
                    encryptPasswordAction.apply(userRegisterRequest.password()),
                    userRegisterRequest.email(),
                    UserRoleConstants.BASIC_ROLE,
                    Instant.now(),
                    Instant.now()
                )
            ).map(userMapper::fromEntity)
            .flatMap(userDto ->
                createAuthTokensAction.apply(userDto, (accessToken, refreshToken) -> new UserRegisterResponse(
                    userDto.uuid(),
                    userDto.username(),
                    userDto.roles(),
                    accessToken,
                    refreshToken
                )));
    }
}
