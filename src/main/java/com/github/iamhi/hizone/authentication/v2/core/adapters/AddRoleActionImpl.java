package com.github.iamhi.hizone.authentication.v2.core.adapters;

import com.github.iamhi.hizone.authentication.v2.config.UserRoleSecretsConfig;
import com.github.iamhi.hizone.authentication.v2.core.actions.CreateAuthTokensAction;
import com.github.iamhi.hizone.authentication.v2.core.dto.UserDto;
import com.github.iamhi.hizone.authentication.v2.core.dto.UserRoleConstants;
import com.github.iamhi.hizone.authentication.v2.core.mappers.UserMapper;
import com.github.iamhi.hizone.authentication.v2.in.shared.UserTokenConsumer;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.AddRoleAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.AddRoleRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.AddRoleResponse;
import com.github.iamhi.hizone.authentication.v2.out.postgres.UserRepository;
import com.github.iamhi.hizone.authentication.v2.out.postgres.models.UserEntity;
import com.github.iamhi.hizone.authentication.v2.out.redis.actions.GetTokenDataAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
record AddRoleActionImpl(
    CreateAuthTokensAction<AddRoleResponse> createAuthTokensAction,
    GetTokenDataAction getTokenDataAction,
    UserRoleSecretsConfig userRoleSecretConfig,
    UserRepository userRepository,
    UserMapper userMapper
) implements AddRoleAction {

    @Override
    public Mono<AddRoleResponse> apply(AddRoleRequest addRoleRequest) {
        return Mono.deferContextual(contextView -> Mono.justOrEmpty(contextView.getOrDefault(UserTokenConsumer.USER_TOKEN_CONTEXT, "")))
            .flatMap(getTokenDataAction)
            .map(userMapper::fromString)
            .flatMap(userDto -> !userDto.roles().contains(addRoleRequest.role()) && isValidRequest(addRoleRequest.role(), addRoleRequest.roleSecret())
                ? addRoleAndSave(userDto, addRoleRequest.role())
                .map(userMapper::fromEntity)
                : Mono.just(userDto))
            .flatMap(userDto -> createAuthTokensAction.apply(userDto, (accessToken, responseToken) -> new AddRoleResponse(
                userDto.uuid(),
                userDto.username(),
                userDto.roles(),
                accessToken,
                responseToken
            )));
    }

    private boolean isValidRequest(String role, String roleSecret) {
        return switch (role) {
            case UserRoleConstants.SERVICE_ROLE -> userRoleSecretConfig.getService().equals(roleSecret);

            case UserRoleConstants.ADMIN_ROLE -> userRoleSecretConfig.getAdmin().equals(roleSecret);

            default -> false;
        };
    }

    private Mono<UserEntity> addRoleAndSave(UserDto userDto, String role) {
        return userRepository.findByUuid(userDto.uuid())
            .map(userEntity -> {
                userEntity.setRoles(userEntity.getRoles().concat("," + role));
                userEntity.setUpdatedAt(Instant.now());

                return userEntity;
            })
            .flatMap(userRepository::save);
    }
}
