package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    /////////////////////////////////authentication/////////////////////////////////
    //    @Override    //usable before 3.0.x as override method of WebSecurityConfigurerAdapter
    //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //        auth.inMemoryAuthentication().withUser("JavaTechie")
    //                .password(passwordEncoder().encode("Password")).roles("ADMIN");
    //        auth.inMemoryAuthentication().withUser("Basant")
    //                .password(passwordEncoder().encode("Password2")).roles("USER");
    //    }

    /*
    @Bean   //This method does not define id for UserDetail, so we need CustomUserDetail to give id as well.
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("Hassan")
                .password(encoder.encode("Pwd1"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("Hussain")
                .password(encoder.encode("Pwd2"))
                .roles("USER", "HR")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
     */

    /**
     * Authentication using CustomUserDetailsService.
     *
     * @param encoder encoder
     * @return UserDetailsService
     */
    @Bean // Here, we can provide id as well. (Write CustomUserDetails class and CustomUserDetailsService for it.)
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return new CustomUserDetailsService(encoder);
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
         return new UserInfoUserDetailsService();
    }
     */

    ///////////////////////////Authorization///////////////////////////
    /* // security for all API (usable before 3.0.x as override method of WebSecurityConfigurerAdapter)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
    }
     */


    /* // security based on URL  (usable before 3.0.x as override method of WebSecurityConfigurerAdapter)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/rest/**").fullyAuthenticated().and
                ().httpBasic();
    }
     */

    /* // security based on ROLE  (usable before 3.0.x as override method of WebSecurityConfigurerAdapter)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/rest/**").hasAnyRole("ADMIN").anyRequest().fullyAuthenticated().and()
                .httpBasic();
    }
     */

    /*
    @Bean   //usable before sb 3.1.x
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/products/welcome", "/products/new").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/products/**")
                .authenticated().and().formLogin().and().build();
    }
     */

    /**
     * Authorization.
     *
     * @param http http
     * @return SecurityFilterChain
     * @throws Exception ex
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers("/api/v1/security/welcome", "/api/v1/security/add-dummy-users",
                                                "/h2-console/**").permitAll()
                                        .requestMatchers("/api/v1/security/**").authenticated()
                        //.anyRequest().authenticated()  // all other URLs be secured (e.g., /home, /api/other, /xyz)
                        // otherwise access denied (403 Forbidden) //All other URLs (e.g., /home, /api/other, /xyz) → not accessible
                )
                .httpBasic(Customizer.withDefaults()).build();    //basic auth, it gives pop up
        //.formLogin(Customizer.withDefaults()).build();  //it gives form for login on /login
    }

//    /**
//     * Since DaoAuthenticationProvider is deprecated and UserDetailsService and PasswordEncoder are beans.
//     * Spring will automatically register a suitable DaoAuthenticationProvider behind the scenes.
//     * You just have to add AuthenticationManager @Bean.
//     */
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService(passwordEncoder()));
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }

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
