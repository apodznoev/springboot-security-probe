package de.avpod.kickout.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author Andrei.Podznoev
 * Date    08.11.2017.
 */
public class QrAuthenticationToken extends AbstractAuthenticationToken {
    private final String qrToken;
    private final String name;

    public QrAuthenticationToken(String qrToken, String authUser) {
        super(Collections.emptyList());
        this.qrToken = qrToken;
        this.name = authUser;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return qrToken + name;
    }
}
