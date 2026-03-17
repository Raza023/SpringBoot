package com.example.aop.controller;

import com.example.aop.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reporting")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    /**
     * Update a report for the given user.
     *
     * @param email The email address of the user to update the report for.
     */
    @PutMapping("/update/{email}")
    public void updateReportByUser(@PathVariable String email) {

        reportingService.updateReportByUser(email);

    }

    /**
     * Get a greeting.
     *
     * @return A greeting string.
     */
    @GetMapping
    public String getGreeting() {
        return "Hello World";

    }

}
