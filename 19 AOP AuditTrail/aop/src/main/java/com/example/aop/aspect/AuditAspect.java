package com.example.aop.aspect;

import com.example.aop.annotation.AuditTrail;
import com.example.aop.entity.ReportingAuditInfo;
import com.example.aop.repository.ReportingAuditInfoRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final ReportingAuditInfoRepository auditRepo;

    /**
     * Log an audit trail.
     *
     * @param joinPoint  The join point.
     * @param auditTrail The audit trail.
     */
    @AfterReturning("@annotation(auditTrail)")
    public void logAudit(JoinPoint joinPoint, AuditTrail auditTrail) {

        Object[] args = joinPoint.getArgs();

        String email = null;

        // Extract email from method arguments
        for (Object arg : args) {
            if (arg instanceof String) {
                email = (String) arg;
                break;
            }
        }

        ReportingAuditInfo log = new ReportingAuditInfo();
        log.setEmail(email);
        log.setAction(auditTrail.action());
        log.setMethodName(joinPoint.getSignature().getName());
        log.setTimestamp(LocalDateTime.now());

        auditRepo.save(log);
    }
}