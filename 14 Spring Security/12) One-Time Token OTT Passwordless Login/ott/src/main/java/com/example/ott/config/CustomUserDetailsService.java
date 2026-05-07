package com.example.ott.config;

import com.example.ott.modal.User;
import com.example.ott.repository.UserDataService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDataService userDataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDataService.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found.");
        }
        return new CustomUserDetails(user.get());
    }
}
