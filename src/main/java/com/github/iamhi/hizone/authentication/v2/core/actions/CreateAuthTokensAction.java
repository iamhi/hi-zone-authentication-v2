package com.github.iamhi.hizone.authentication.v2.core.actions;

import com.github.iamhi.hizone.authentication.v2.core.dto.UserDto;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

public interface CreateAuthTokensAction<T> extends BiFunction<UserDto, BiFunction<String, String, T>, Mono<T>> {
}
