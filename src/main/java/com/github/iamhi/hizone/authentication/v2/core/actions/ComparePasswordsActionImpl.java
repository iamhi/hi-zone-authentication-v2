package com.github.iamhi.hizone.authentication.v2.core.actions;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
record ComparePasswordsActionImpl(
    BCryptPasswordEncoder bCryptPasswordEncoder
) implements ComparePasswordsAction {

    @Override
    public Boolean apply(String originalPassword, String encryptedPassword) {
        return bCryptPasswordEncoder.matches(originalPassword, encryptedPassword);
    }
}
