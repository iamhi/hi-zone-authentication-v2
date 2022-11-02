package com.github.iamhi.hizone.authentication.v2.in.user.actions;

import com.github.iamhi.hizone.authentication.v2.in.user.requests.AddRoleRequest;
import com.github.iamhi.hizone.authentication.v2.in.user.responses.AddRoleResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface AddRoleAction extends Function<AddRoleRequest, Mono<AddRoleResponse>> {
}
