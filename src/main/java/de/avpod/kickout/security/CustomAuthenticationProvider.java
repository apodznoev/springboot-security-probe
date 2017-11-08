package de.avpod.kickout.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Andrei.Podznoev
 * Date    08.11.2017.
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        QrAuthenticationToken tokenAuth = (QrAuthenticationToken) auth;
        String qrToken = tokenAuth.getCredentials();

        if (qrToken == null) {
            log.info("No QR tokenAuth was provided");
            throw new BadCredentialsException("No QR tokenAuth was provided");
        }

        if ("good".equals(qrToken)) {
            log.info("Authenticating the token {}", qrToken);
            tokenAuth.setAuthenticated(true);
            return tokenAuth;
        } else {
            log.info("Uknown token {}", qrToken);
            throw new BadCredentialsException("QR tokenAuth is invalid");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(QrAuthenticationToken.class);
    }
}
