package com.github.iamhi.hizone.authentication.v2.in.user.actions;

import com.github.iamhi.hizone.authentication.v2.in.user.requests.ChangePasswordRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.ChangePasswordResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface ChangePasswordAction extends Function<ChangePasswordRequest, Mono<ChangePasswordResponse>> {
}
