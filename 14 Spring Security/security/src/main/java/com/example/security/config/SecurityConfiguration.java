package com.example.security.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   //necessary for @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_HR')")
public class SecurityConfiguration {

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

    @Bean   // Here, we can provide id as well. (Write CustomUserDetails class for it.)
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        CustomUserDetails admin = new CustomUserDetails(
                1L,
                "Hassan",
                encoder.encode("Pwd1"),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        CustomUserDetails user = new CustomUserDetails(
                2L,
                "Hussain",
                encoder.encode("Pwd2"),
                List.of(
                        new SimpleGrantedAuthority("ROLE_USER")
                        //, new SimpleGrantedAuthority("ROLE_HR")  //I don't want to give hr role
                )
        );

        return new InMemoryUserDetailsManager(List.of(admin, user));
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v1/security/welcome", "/api/v1/security/add-dummy-users",
                                        "/h2-console/**").permitAll()
                                .requestMatchers("/api/v1/security/**").authenticated()
                )
                .httpBasic(Customizer.withDefaults()).build();    //basic auth, it gives pop up
        //.formLogin(Customizer.withDefaults()).build();  //it gives form for login on /login
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
}
