package com.github.iamhi.hizone.authentication.v2.in.user.responses;

import java.util.List;

public record UserRegisterResponse(
    String uuid,
    String username,
    List<String> roles,
    String accessToken,
    String refreshToken
) {
}
