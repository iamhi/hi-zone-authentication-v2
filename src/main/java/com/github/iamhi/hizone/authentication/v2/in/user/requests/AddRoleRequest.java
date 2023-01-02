package com.github.iamhi.hizone.authentication.v2.in.user.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    title = "AddRoleRequest",
    description = "Add role with role secret request"
)
public record AddRoleRequest(
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "service") String role,
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "super-secretive-secret") String roleSecret
) {
}
