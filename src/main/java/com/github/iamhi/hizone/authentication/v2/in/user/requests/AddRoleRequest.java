package com.github.iamhi.hizone.authentication.v2.in.user.requests;

public record AddRoleRequest(
    String role,
    String roleSecret
) {
}
