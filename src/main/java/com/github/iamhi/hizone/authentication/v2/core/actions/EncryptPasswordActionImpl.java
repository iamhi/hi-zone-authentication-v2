package com.github.iamhi.hizone.authentication.v2.core.actions;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
record EncryptPasswordActionImpl(
    BCryptPasswordEncoder bCryptPasswordEncoder
) implements EncryptPasswordAction {
    @Override
    public String apply(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
