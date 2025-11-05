package com.example.identity.service.config;

import com.example.identity.service.jwt.JwtAccessDeniedHandler;
import com.example.identity.service.jwt.JwtAuthenticationEntryPoint;
import com.example.identity.service.jwt.JwtFilter;
import com.example.identity.service.repository.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity   //necessary for @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_HR')")
public class AuthConfig {
/////////////////////////////////PasswordEncoder/////////////////////////////////

    private final UserDataService userDataService;
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    /**
     * Password Encoder.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for stateless JWT
                .authorizeHttpRequests(auth ->  // Set authorization rules
                                auth.requestMatchers("/auth/register", "/auth/token", "/auth/validate").permitAll()
                                        .requestMatchers("/api/v1/security/**").authenticated()
                        //.anyRequest().authenticated()  // all other URLs will be secured (e.g., /home, /api/other, /xyz)
                        // otherwise access denied (403 Forbidden) //All other URLs (e.g., /home, /api/other, /xyz) → not accessible
                ).exceptionHandling(ex -> ex
                        //Unauthorized access (no token or invalid token) handled by a custom AuthenticationEntryPoint.
                        .authenticationEntryPoint(entryPoint)
                        //Access denied (valid token, but lacking roles) handled by a custom AccessDeniedHandler.
                        .accessDeniedHandler(accessDeniedHandler)
                )
                // Enforce stateless session management
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add our JWT filter before the default UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults()).build();    //basic auth, it gives pop up
        //.formLogin(Customizer.withDefaults()).build();  //it gives form for login on /login
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

