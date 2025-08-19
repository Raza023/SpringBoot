package com.example.security.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final OAuth2AuthorizedClientService clientService;

    @GetMapping
    public String welcome() {
        return "Welcome to Google !!";
    }

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

    @GetMapping("/full-profile")
    public Map<String, Object> getFullProfile(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        String accessToken = client.getAccessToken().getTokenValue();
        WebClient webClient = WebClient.create();
        return webClient
                .get()
                .uri("https://people.googleapis.com/v1/people/me?personFields=names,emailAddresses,birthdays,genders,phoneNumbers,photos,addresses")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                }).block();
    }

    @GetMapping("/user-profile")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User oauth2User) {
        return oauth2User.getAttributes(); // includes name, email, picture
    }

    @GetMapping("/user/name")
    public String userName(@AuthenticationPrincipal OAuth2User oauth2User) {
        return (String) oauth2User.getAttributes().get("name"); // includes name, email, picture
    }


}
