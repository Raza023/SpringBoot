package com.example.aop.repository;

import com.example.aop.entity.ReportingInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingInfoRepository extends JpaRepository<ReportingInfo, Long> {

    // Add custom queries here if needed
    List<ReportingInfo> findByEmail(String email);

    List<ReportingInfo> findByAccountNo(String accountNo);

}
