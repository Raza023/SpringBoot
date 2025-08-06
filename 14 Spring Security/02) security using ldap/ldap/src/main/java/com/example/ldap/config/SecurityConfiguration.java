package com.example.ldap.config;


import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   //necessary for @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_HR')")
public class SecurityConfiguration {

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

//    /////////////////////////////////authentication/////////////////////////////////
//    /**
//     * Authentication using CustomUserDetailsService.
//     *
//     * @param encoder encoder
//     * @return UserDetailsService
//     */
//    @Bean // Here, we can provide id as well. (Write CustomUserDetails class and CustomUserDetailsService for it.)
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        return new CustomUserDetailsService(encoder);
//    }


    /**
     * Defines a Spring Security LDAP for binding and searching LDAP users.
     */
    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource(
                List.of("ldap://localhost:8389/"),
                "dc=springframework,dc=org"
        );
    }

    ///////////////////////////Authorization///////////////////////////

    /**
     * Authorization.
     *
     * @param http http
     * @return SecurityFilterChain
     * @throws Exception ex
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource(contextSource())
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");

        AuthenticationManager authManager = authBuilder.build();
        return http.csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authManager)
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers("/h2-console/**").permitAll()
                                        .requestMatchers("/api/v1/ldap/secure").authenticated()
                        //.anyRequest().authenticated()  // all other URLs be secured (e.g., /home, /api/other, /xyz)
                        // otherwise access denied (403 Forbidden) //All other URLs (e.g., /home, /api/other, /xyz) → not accessible
                )
                .httpBasic(Customizer.withDefaults()).build();    //basic auth, it gives pop up
        //.formLogin(Customizer.withDefaults()).build();  //it gives form for login on /login
    }

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
