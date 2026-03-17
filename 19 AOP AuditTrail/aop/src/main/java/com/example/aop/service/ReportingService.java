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
     * Updates a report for the given user.
     *
     * @param email The email address of the user to update the report for.
     */
    @AuditTrail(action = "UPDATE_REPORT")
    public void updateReportByUser(String email) {
        System.out.println("Updating report for: " + email);

        ReportingInfo reportingInfo = new ReportingInfo();
        reportingInfo.setEmail(email);
        reportingInfo.setAmount(new BigDecimal(100));
        reportingInfo.setCredit(true);
        reportingInfo.setDebit(false);
        reportingInfo.setAccountNo("1234567890");
        reportingInfoRepository.save(reportingInfo);

    }
}