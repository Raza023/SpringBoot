package com.example.security.config;

import com.example.security.model.User;
import com.example.security.repository.UserDataService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new UsernameNotFoundException("User not found.");
        }
        return new CustomUserDetails(user.get());
        // same as above
        // return user.map(CustomUserDetails::new)
        //         .orElseThrow(() -> new UsernameNotFoundException("User not found exception."));
    }
}
