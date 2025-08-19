package com.example.security.config;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults()); // Enables OAuth2 login
        return http.build();
    }

    //Add following bean if your system is not synchronized to local time.
    //Otherwise, there is no need of it.
    @Bean
    public OidcIdTokenDecoderFactory oidcIdTokenDecoderFactory() {
        OidcIdTokenDecoderFactory factory = new OidcIdTokenDecoderFactory();
        factory.setJwtValidatorFactory(clientRegistration -> {
            OAuth2TokenValidator<Jwt> defaultValidator = JwtValidators.createDefaultWithIssuer(
                    clientRegistration.getProviderDetails().getIssuerUri());
            return (OAuth2TokenValidator<Jwt>) new DelegatingOAuth2TokenValidator<Jwt>(
                    defaultValidator,
                    new JwtTimestampValidator(Duration.ofMinutes(5)) // ⏰ 5 minute skew
            );
        });
        return factory;
    }

}