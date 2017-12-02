package de.avpod.kickout.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                .antMatchers("/api/v1/landing")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .anonymous().disable()
                .addFilterAt(new CustomTokenAuthenticationFilter("/api/v1/landing"), AnonymousAuthenticationFilter.class)
        ;
    }


    @Component( "restAuthenticationEntryPoint" )
    public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


        @Override
        public void commence(javax.servlet.http.HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {
            response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
        }
    }

}