package com.example.facebook.config;

import com.example.facebook.entity.User;
import com.example.facebook.repository.UserDataService;
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
        Optional<User> user = userDataService.findByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User {"+username+"} not found.");
        }
        return new CustomUserDetails(user.get());
        // same as above
        // return user.map(CustomUserDetails::new)
        //         .orElseThrow(() -> new UsernameNotFoundException("User not found exception."));
    }
}
