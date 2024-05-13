package dev.fastball.portal.core.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public PasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
