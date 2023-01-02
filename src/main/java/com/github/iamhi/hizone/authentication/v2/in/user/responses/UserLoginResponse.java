package com.github.iamhi.hizone.authentication.v2.in.user.responses;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
    title = "UserLoginResponse",
    description = "Successful user login response"
)
public record UserLoginResponse(
    @Schema(type = "string", example = "d964cef9-a094-47fa-baa4-4a8ce85c5087") String uuid,
    @Schema(type = "string", example = "username") String username,
    @ArraySchema(
        schema = @Schema(type = "string", example = "basic"),
        uniqueItems = true,
        minItems = 1
    ) List<String> roles,
    @Schema(type = "string", example = "d964cef9-a094-47fa-baa4-4a8ce85c5087") String accessToken,
    @Schema(type = "string", example = "d964cef9-a094-47fa-baa4-4a8ce85c5087") String refreshToken
) {
}
