package com.example.keycloak.controller;

import com.example.keycloak.entity.Employee;
import com.example.keycloak.service.EmployeeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//https://developers.redhat.com/articles/2023/07/24/how-integrate-spring-boot-3-spring-security-and-keycloak#install_keycloak
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    //this method can be accessed by user whose role is user
    @GetMapping(value = "/emp/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@RolesAllowed("user")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
        return ResponseEntity.ok(service.getEmployee(id));
    }

    //Use @PreAuthorize if you need flexibility (e.g., roles + authorities + custom conditions).
    //Use @RolesAllowed if you want a clean, portable, role-only security annotation.
    //@PreAuthorize("hasRole('admin')") is much more flexible than @RolesAllowed("admin")
    // We can use following as well:
    // 1) hasAnyRole('admin','manager')
    // 2) hasAuthority('SCOPE_read')
    // 3) #id == authentication.principal.id (custom conditions)

    //this method can be accessed by user whose role is admin
    @GetMapping(value = "/emp", produces = MediaType.APPLICATION_JSON_VALUE)
    //@RolesAllowed("admin")  //same as below (use one at a time)
    @PreAuthorize("hasRole('admin')")
    public List<Employee> findALlEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/debug-authorities")
    public ResponseEntity<Map<String, Object>> debugAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> result = new HashMap<>();
        result.put("authenticated", authentication.isAuthenticated());
        result.put("principal", authentication.getPrincipal());
        result.put("authorities", authentication.getAuthorities());
        result.put("credentials", authentication.getCredentials());
        result.put("details", authentication.getDetails());
        System.out.println("Authentication: " + authentication);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/debug-claims")
    public ResponseEntity<Map<String, Object>> debugClaims(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(jwt.getClaims());
    }

}
