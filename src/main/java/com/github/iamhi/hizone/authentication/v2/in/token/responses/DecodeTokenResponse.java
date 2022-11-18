package com.github.iamhi.hizone.authentication.v2.in.token.responses;

import java.util.List;

public record DecodeTokenResponse(
    String uuid,
    String username,
    List<String> roles
) {
}
