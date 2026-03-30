package com.example.aop.service;

import com.example.aop.annotation.AuditTrail;
import com.example.aop.entity.ReportingInfo;
import com.example.aop.repository.ReportingInfoRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final ReportingInfoRepository reportingInfoRepository;

    /**
     * Updates a report for the given user by saving a new ReportingInfo object. The updated report will have the given
     * user's email address, an amount of 100, and credit set to true and debit set to false. The account number will be
     * set to "1234567890".
     *
     * @param email The email address of the user to update the report for.
     */
    @AuditTrail(action = "UPDATE_REPORT")
    public void updateReportByUser(String email) {
        // Create a new ReportingInfo object with the given email address and default values
        ReportingInfo reportingInfo = ReportingInfo.builder()
                .email(email)
                .amount(new BigDecimal(100))
                .credit(true)
                .debit(false)
                .accountNo("1234567890")
                .build();

        // Save the new ReportingInfo object
        reportingInfoRepository.save(reportingInfo);
    }

}