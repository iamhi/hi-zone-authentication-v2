package com.github.iamhi.hizone.authentication.v2.in.shared;

import com.github.iamhi.hizone.authentication.v2.core.exceptions.InvalidLoginThrowable;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.InvalidRefreshTokenThrowable;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.UserCreationErrorThrowable;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.InvalidAuthorizationTokenThrowable;
import com.github.iamhi.hizone.authentication.v2.core.exceptions.UserNotFoundThrowable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public record RequestExceptionHandler() implements Function<Throwable, Mono<ServerResponse>> {

    @Override
    public Mono<ServerResponse> apply(Throwable throwable) {

        return switch (throwable) {
            case UserCreationErrorThrowable userCreationErrorThrowable ->
                ServerResponse.badRequest().bodyValue(userCreationErrorThrowable.getMessage());

            case InvalidAuthorizationTokenThrowable invalidAuthorizationTokenThrowable ->
                ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue(invalidAuthorizationTokenThrowable.getMessage());

            case InvalidRefreshTokenThrowable invalidRefreshTokenThrowable ->
                ServerResponse.status(HttpStatus.FORBIDDEN).bodyValue(invalidRefreshTokenThrowable.getMessage());

            case UserNotFoundThrowable userNotFoundThrowable ->
                ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(userNotFoundThrowable.getMessage());

            case InvalidLoginThrowable invalidLoginThrowable ->
                ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(invalidLoginThrowable.getMessage());

            default -> {
                throwable.printStackTrace();

                yield ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        };
    }
}
