package com.github.iamhi.hizone.authentication.v2.in.user.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    title = "UserLoginRequest",
    description = "Login with username and password request"
)
public record UserLoginRequest(
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "username") String username,
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "password") String password
) {
}
