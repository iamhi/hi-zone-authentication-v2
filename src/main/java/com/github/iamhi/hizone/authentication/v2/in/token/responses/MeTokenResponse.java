package com.github.iamhi.hizone.authentication.v2.in.token.responses;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
    title = "DecodeTokenResponse",
    description = "Successfully decode a token response"
)
public record MeTokenResponse (
    @Schema(type = "string", example = "d964cef9-a094-47fa-baa4-4a8ce85c5087")
    String uuid,

    @Schema(type = "string", example = "username")
    String username,

    @ArraySchema(
        schema = @Schema(type = "string", example = "basic"),
        uniqueItems = true,
        minItems = 1
    )
    List<String> roles
) {
}