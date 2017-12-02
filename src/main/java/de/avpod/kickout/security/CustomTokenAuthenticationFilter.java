package de.avpod.kickout.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by apodznoev
 * date 03.12.2017.
 */
public class CustomTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomTokenAuthenticationFilter.class);

    private final static String TOKEN_PARAM = "qrSourceId";

    public CustomTokenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationSuccessHandler(new TokenSimpleUrlAuthenticationSuccessHandler());
    }


    /**
     * Attempt to authenticate request - basically just pass over to another method to authenticate request headers
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws AuthenticationException, IOException {
        String token = request.getParameter(TOKEN_PARAM);
        logger.info("token found:" + token);

        if (!"good".equals(token))
            throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));

        return new QrAuthenticationToken(token, "AuthUser");
    }

    public class TokenSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        protected String determineTargetUrl(HttpServletRequest request,
                                            HttpServletResponse response) {
            String context = request.getContextPath();
            String fullURL = request.getRequestURI();
            return context + "/index.html";
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String url = determineTargetUrl(request, response);
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

}
