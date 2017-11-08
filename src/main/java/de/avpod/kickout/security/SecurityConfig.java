package de.avpod.kickout.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

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
                .antMatchers("/**/unknownVisit", "/**/hello")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new HttpRequestToTokenFilter(authenticationManager), AnonymousAuthenticationFilter.class)
        ;
    }

    @Bean
    public CustomAuthenticationProvider springAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

}