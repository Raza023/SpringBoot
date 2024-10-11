package com.example.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

    //2) second method  (to completely bypass the security)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/ticket", "GET"))
                .requestMatchers(new AntPathRequestMatcher("/ticket/**", "GET"))
                .requestMatchers(new AntPathRequestMatcher("/ticket/search", "GET"))
                .requestMatchers(new AntPathRequestMatcher("/ticket/search/**", "GET"))
                .requestMatchers(new AntPathRequestMatcher("/ticket", "POST"))
                .requestMatchers(new AntPathRequestMatcher("/ticket/**", "POST"))
                .requestMatchers(new AntPathRequestMatcher("/ticket/search", "POST"))
                .requestMatchers(new AntPathRequestMatcher("/ticket/search/**", "POST"))
                .requestMatchers(new AntPathRequestMatcher("/actuator", "GET"))
                .requestMatchers(new AntPathRequestMatcher("/actuator/**", "GET"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .formLogin(form -> form
                        .successHandler((request, response, authentication) -> {
                            // Redirect to the desired URL after login
                            response.sendRedirect("http://localhost:9080/ticket/getTickets");
                        })
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .headers(headers -> headers
                        .addHeaderWriter(new ReferrerPolicyHeaderWriter(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").hasAnyAuthority("ACTUATOR")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
    }
}


