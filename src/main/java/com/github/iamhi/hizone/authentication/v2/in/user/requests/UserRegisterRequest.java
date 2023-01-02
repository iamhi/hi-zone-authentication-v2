package com.github.iamhi.hizone.authentication.v2.in.user.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    title = "UserRegisterRequest",
    description = "Register with username, password and email"
)
public record UserRegisterRequest(
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "username") String username,
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "password") String password,
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@example.com") String email
) {
}
