package com.github.iamhi.hizone.authentication.v2.in.token.requests;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    title = "RefreshTokensRequest",
    description = "A request for new access token with refresh token"
)
public record RefreshTokensRequest(
    @Schema(type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "d964cef9-a094-47fa-baa4-4a8ce85c5087") String refreshToken
) {
}
