package com.github.iamhi.hizone.authentication.v2.core.adapters;

import com.github.iamhi.hizone.authentication.v2.core.actions.ComparePasswordsAction;
import com.github.iamhi.hizone.authentication.v2.core.actions.CreateAuthTokensAction;
import com.github.iamhi.hizone.authentication.v2.core.actions.EncryptPasswordAction;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.InvalidLoginThrowable;
import com.github.iamhi.hizone.authentication.v2.core.mappers.UserMapper;
import com.github.iamhi.hizone.authentication.v2.in.user.actions.ChangePasswordAction;
import com.github.iamhi.hizone.authentication.v2.in.user.requests.ChangePasswordRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.ChangePasswordResponse;
import com.github.iamhi.hizone.authentication.v2.out.postgres.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public record ChangePasswordActionImpl (
    UserRepository userRepository,
    ComparePasswordsAction comparePasswordsAction,
    CreateAuthTokensAction<ChangePasswordResponse> createAuthTokensAction,
    EncryptPasswordAction encryptPasswordAction,
    UserMapper userMapper
) implements ChangePasswordAction {

    @Override
    public Mono<ChangePasswordResponse> apply(ChangePasswordRequest changePasswordRequest) {
        return userRepository.findByUsername(changePasswordRequest.username())
            .switchIfEmpty(Mono.error(InvalidLoginThrowable::new))
            .flatMap(userEntity -> {
                if (Boolean.TRUE.equals(comparePasswordsAction.apply(changePasswordRequest.oldPassword(), userEntity.getPassword()))) {
                    userEntity.setPassword(encryptPasswordAction.apply(changePasswordRequest.newPassword()));

                    return userRepository.save(userEntity).map(userMapper::fromEntity);
                }

                return Mono.error(InvalidLoginThrowable::new);
            })
            .flatMap(userDto -> createAuthTokensAction.apply(userDto, ChangePasswordResponse::new));
    }
}
