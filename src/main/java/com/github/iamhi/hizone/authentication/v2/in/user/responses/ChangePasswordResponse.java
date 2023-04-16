package com.github.iamhi.hizone.authentication.v2.in.user.responses;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChangePasswordResponse(
    @Schema(type = "string", example = "d964cef9-a094-47fa-baa4-4a8ce85c5087") String accessToken,
    @Schema(type = "string", example = "d964cef9-a094-47fa-baa4-4a8ce85c5087") String refreshToken
) {
}
