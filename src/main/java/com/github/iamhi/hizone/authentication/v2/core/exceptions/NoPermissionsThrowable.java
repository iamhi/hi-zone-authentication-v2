package com.github.iamhi.hizone.authentication.v2.core.exceptions;

public class NoPermissionsThrowable extends Throwable {
    public NoPermissionsThrowable() {
        super("Missing permissions for this action");
    }
}
