package com.github.iamhi.hizone.authentication.v2.in.user.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    title = "ChangePasswordRequest",
    description = "Change the password request"
)
public record ChangePasswordRequest (
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "username") String username,
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "oldPassword") String oldPassword,
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "newPassword") String newPassword
) {}
