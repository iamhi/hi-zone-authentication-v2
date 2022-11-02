package com.github.iamhi.hizone.authentication.v2.in.user.actions;

import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserRegisterRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.UserRegisterResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface UserRegisterAction extends Function<UserRegisterRequest, Mono<UserRegisterResponse>> {
}
