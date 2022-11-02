package com.github.iamhi.hizone.authentication.v2.core.dto;

import java.util.List;

public record UserDto(
    String uuid,
    String username,
    String email,
    List<String> roles
) {
}
