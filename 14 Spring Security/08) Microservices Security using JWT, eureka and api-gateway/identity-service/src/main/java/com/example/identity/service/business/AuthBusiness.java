package com.example.identity.service.business;

import com.example.identity.service.entity.UserInfo;
import com.example.identity.service.jwt.JwtUtil;
import com.example.identity.service.repository.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthBusiness {

    private final UserDataService userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public String saveUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.saveAndFlush(userInfo);
        return "User added to the system successfully.";
    }

    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public Boolean validateToken(String token, String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.validateToken(token, userDetails);
    }

}
