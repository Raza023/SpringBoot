package com.example.security.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, CustomUserDetails> users = new HashMap<>();

    public CustomUserDetailsService(PasswordEncoder encoder) {
        users.put("Hassan", new CustomUserDetails(
                1L, "Hassan", encoder.encode("Pwd1"),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        ));
        users.put("Hussain", new CustomUserDetails(
                2L, "Hussain", encoder.encode("Pwd2"),
                List.of(
                        new SimpleGrantedAuthority("ROLE_USER"),
                        new SimpleGrantedAuthority("ROLE_HR")
                )
        ));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!users.containsKey(username)) {
            throw new UsernameNotFoundException("User not found");
        }
        return users.get(username); // returns your CustomUserDetails
    }
}
