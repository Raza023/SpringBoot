package com.example.ott.config;

import com.example.ott.handler.OttSuccessHandler;
import com.example.ott.repository.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ott.InMemoryOneTimeTokenService;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity      //Activate web security support and allow me to customize it.
@EnableMethodSecurity   //necessary for @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_HR')")
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDataService userDataService;
    private final OttSuccessHandler ottSuccessHandler;

    /////////////////////////////////PasswordEncoder/////////////////////////////////

    /**
     * Password Encoder.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OneTimeTokenService oneTimeTokenService() {
        return new InMemoryOneTimeTokenService();
    }

    /////////////////////////////////Authentication/////////////////////////////////

    /**
     * Authentication using CustomUserDetailsService.
     *
     * @return UserDetailsService
     */
    @Bean // Here, we can provide id as well. (Write CustomUserDetails class and CustomUserDetailsService for it.)
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userDataService);
    }

    ///////////////////////////Authorization///////////////////////////

    /**
     * Authorization using SecurityFilterChain.
     *
     * @param http http
     * @return SecurityFilterChain
     * @throws Exception ex
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for stateless JWT
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
                .authorizeHttpRequests(// Set authorization rules
                        auth -> auth.requestMatchers(
                                        "/api/v1/login",
                                        "/api/v1/signup",
                                        "/h2-console/**",
                                        "/api/v1/login-ott",
                                        "/api/v1/login-with-ott",
                                        "/api/v1/ott-sent"
                                ).permitAll()
                                //.requestMatchers("/api/v1/security/**").authenticated()
                                .anyRequest().authenticated() // Now all other URLs will be secured
                        // (e.g., /home, /api/other, /xyz)
                        // otherwise access denied (403 Forbidden)
                        // All other URLs (e.g., /home, /api/other, /xyz) → not accessible
                )
                //.httpBasic(Customizer.withDefaults()) //basic auth, it gives pop up
                //.formLogin(Customizer.withDefaults()).build();  //it gives form for login on /login
                .formLogin(form -> form
                        .loginPage("/api/v1/login")
                        //.defaultSuccessUrl("/api/v1/index", true)
                        .successHandler((req, res, auth) ->
                                res.sendRedirect("/api/v1/index"))
                )
                .logout(logout -> logout
                        //This endpoint is managed by spring security.
                        .logoutSuccessUrl("/api/v1/login?logout")
                )
                .oneTimeTokenLogin(ott -> ott
                        .loginPage("/api/v1/login")
                        // STEP 1 → generate token (this endpoint is managed by spring security)
                        .tokenGeneratingUrl("/api/v1/ott")
                        // STEP 2 → verify token (this endpoint is managed by spring security)
                        .loginProcessingUrl("/api/v1/ott/verify")
                        .tokenGenerationSuccessHandler(ottSuccessHandler)
                        .successHandler((req, res, auth) ->
                                res.sendRedirect("/api/v1/index"))
                )
                .build();
    }

    ///////////////////////////AuthenticationManager///////////////////////////

    /**
     * Spring will automatically create a suitable DaoAuthenticationProvider internally. Using your UserDetailsService
     * and PasswordEncoder beans.
     *
     * @param config config
     * @return AuthenticationManager
     * @throws Exception ex
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
