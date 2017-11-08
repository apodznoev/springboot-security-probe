package de.avpod.kickout.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * @author Andrei.Podznoev
 * Date    08.11.2017.
 */
public class QrAuthenticationToken extends AbstractAuthenticationToken {
    private final String qrToken;

    public QrAuthenticationToken(String qrToken) {
        super(Collections.emptyList());
        this.qrToken = qrToken;
    }

    @Override
    public String getCredentials() {
        return qrToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
