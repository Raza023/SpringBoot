package com.example.aop.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Reporting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Amount")
    private BigDecimal amount;

    @Column(name = "Credit")
    private boolean credit;

    @Column(name = "Debit")
    private boolean debit;

    @Column(name = "AccountNo")
    private String accountNo;
}
