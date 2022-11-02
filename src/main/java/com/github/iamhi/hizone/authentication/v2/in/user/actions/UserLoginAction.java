package com.github.iamhi.hizone.authentication.v2.in.user.actions;

import com.github.iamhi.hizone.authentication.v2.in.user.requests.UserLoginRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.UserLoginResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface UserLoginAction extends Function<UserLoginRequest, Mono<UserLoginResponse>> {
}
