package com.example.facebook.controller;

import com.example.facebook.entity.User;
import com.example.facebook.repository.UserDataService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String MODERATOR_ROLE = "ROLE_MODERATOR";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public String joinGroup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(List.of(DEFAULT_ROLE)));
        userDataService.save(user);
        return "Hi {" + user.getUserName() + "}! Welcome to group.";
    }

    @GetMapping("/access/{userId}/{userRole}")
    public String grantAccessToUser(@PathVariable int userId, @PathVariable String userRole, Principal principal) {
        User granteeUser = userDataService.findById((long) userId).orElse(null);
        if (ObjectUtils.isEmpty(granteeUser)) {
            return "User with id {" + userId + "} does not exist.";
        } else {
            List<String> granterUserRoles = getRolesOfLoggedInUser(principal);
            if (DEFAULT_ROLE.equals(userRole)) {
                return "User already has " + DEFAULT_ROLE;
            } else if (MODERATOR_ROLE.equals(userRole) && (granterUserRoles.contains(MODERATOR_ROLE)
                    || granterUserRoles.contains(ADMIN_ROLE))) {
                if (granteeUser.getRoles().contains(MODERATOR_ROLE)) {
                    return "User already has " + MODERATOR_ROLE;
                } else {
                    granteeUser.getRoles().add(MODERATOR_ROLE);
                    userDataService.save(granteeUser);
                    return "Moderator role is granted to the user with id {"+userId+"}.";
                }
            } else if (ADMIN_ROLE.equals(userRole) && granterUserRoles.contains(ADMIN_ROLE)) {
                if (granteeUser.getRoles().contains(ADMIN_ROLE)) {
                    return "User already has " + ADMIN_ROLE;
                } else {
                    granteeUser.getRoles().add(ADMIN_ROLE);
                    userDataService.save(granteeUser);
                    return "Admin role is granted to the user with id {"+userId+"}.";
                }
            }
        }
        return "";
    }

    private boolean hasModeratorAccess(List<String> roles) {
        return roles.contains(MODERATOR_ROLE) || roles.contains(ADMIN_ROLE);
    }

    private List<String> getRolesOfLoggedInUser(Principal principal) {
        Optional<User> user = getLoggedInUser(principal);
        return user.map(User::getRoles).orElse(null);
    }

    private Optional<User> getLoggedInUser(Principal principal) {
        return userDataService.findByName(principal.getName());
    }

}
