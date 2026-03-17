package com.example.aop.repository;

import com.example.aop.entity.ReportingAuditInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingAuditInfoRepository extends JpaRepository<ReportingAuditInfo, Long> {

}
