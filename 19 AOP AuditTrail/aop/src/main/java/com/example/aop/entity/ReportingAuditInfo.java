package com.example.aop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ReportingAudit")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportingAuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Email")
    private String email;

    @Column(name = "Action")
    private String action;

    @Column(name = "MethodName")
    private String methodName;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

}