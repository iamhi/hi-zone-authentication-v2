package com.github.iamhi.hizone.authentication.v2.in.token.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    title = "DecodeTokenRequest",
    description = "A request for decoding token"
)
public record DecodeTokenRequest(
    @Schema(type = "string", requiredMode = Schema.RequiredMode.REQUIRED, example = "d964cef9-a094-47fa-baa4-4a8ce85c5087")
    String token
) {
}
