package com.example.security.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user-related endpoints in the OAuth2 Google Sign-In flow.
 * Provides endpoints for basic welcome, user info, full Google profile, and extracting user attributes.
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    /**
     * Endpoint to display a welcome message.
     * @return A simple welcome string.
     */
    @GetMapping
    public String welcome() {
        return "Welcome to Google !!";
    }

    /**
     * Endpoint to retrieve basic user information from the OAuth2 authentication token.
     * @param authentication The OAuth2 authentication token containing user details.
     * @return A map with user's email, full name, first name, last name, and profile picture URL.
     */
    @GetMapping("/user")
    public Map<String, Object> user(OAuth2AuthenticationToken authentication) {
        OAuth2User user = authentication.getPrincipal();
        Map<String, Object> attributes = user.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String givenName = (String) attributes.get("given_name");
        String familyName = (String) attributes.get("family_name");
        String picture = (String) attributes.get("picture");
        //String birthdate = (String) attributes.get("birthdate"); // Likely null unless you add scope

        System.out.println("Email: " + email);
        System.out.println("Full Name: " + name);
        System.out.println("Picture: " + picture);
        //System.out.println("Birthdate: " + birthdate);

        return Map.of(
                "email", email,
                "fullName", name,
                "firstName", givenName,
                "lastName", familyName,
                "picture", picture
        );
    }


    /**
     * Endpoint to retrieve all attributes of the authenticated OAuth2 user.
     * @param oauth2User The authenticated OAuth2 user.
     * @return A map of user attributes (name, email, picture, etc.).
     */
    @GetMapping("/user-profile")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User oauth2User) {
        return oauth2User.getAttributes(); // includes name, email, picture
    }

    /**
     * Endpoint to retrieve the name of the authenticated OAuth2 user.
     * @param oauth2User The authenticated OAuth2 user.
     * @return The user's name as a string.
     */
    @GetMapping("/user/name")
    public String userName(@AuthenticationPrincipal OAuth2User oauth2User) {
        return (String) oauth2User.getAttributes().get("name"); // includes name, email, picture
    }


}
