package de.avpod.kickout.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author Andrei.Podznoev
 * Date    08.11.2017.
 */
public class HttpRequestToTokenFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final AuthenticationManager authenticationManager;

    public HttpRequestToTokenFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(javax.servlet.http.HttpServletRequest request) {
        return authenticationManager.authenticate(new QrAuthenticationToken(request.getParameter("qrSourceId")));
    }

    @Override
    protected Object getPreAuthenticatedCredentials(javax.servlet.http.HttpServletRequest request) {
        return request.getParameter("qrSourceId");
    }
}
