package de.avpod.avira;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestAttributeAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by apodznoev
 * date 05.11.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager();
        http
                .authorizeRequests()
                .antMatchers("/**/unknownVisit", "/**/hello").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new AbstractPreAuthenticatedProcessingFilter() {
                    @Override
                    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
                        return authenticationManager.authenticate(new AbstractAuthenticationToken(Collections.emptyList()) {
                            @Override
                            public Object getCredentials() {
                                return request.getParameter("qrSourceId");
                            }

                            @Override
                            public Object getPrincipal() {
                                return request.getParameter("qrSourceId");
                            }
                        });
                    }

                    @Override
                    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
                        return request.getParameter("qrSourceId");
                    }
                },BasicAuthenticationFilter.class)
//                .addFilterBefore(new AbstractAuthenticationProcessingFilter("/**") {
//
//                    @Override
//                    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//                        return authenticationManager.authenticate(
//                                new UsernamePasswordAuthenticationToken(
//                                        "test",
//                                        "secret",
//                                        new ArrayList<>())
//                        );
//                    }
//                }, BasicAuthenticationFilter.class);
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication auth) throws AuthenticationException {
                String username = auth.getName();
                String password = auth.getCredentials()
                        .toString();

                if ("test".equals(username) && "secret".equals(password)) {
                    return new UsernamePasswordAuthenticationToken
                            (username, password, Collections.emptyList());
                } else {
                    throw new
                            BadCredentialsException("External system authentication failed");
                }
            }

            @Override
            public boolean supports(Class<?> auth) {
                return auth.equals(UsernamePasswordAuthenticationToken.class);
            }
        });
    }
}