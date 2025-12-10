package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RefreshToken")
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Token")
    private String token;

    @Column(name = "ExpiryDate")
    private Instant expiryDate;

    @JsonIgnore // Prevent recursion during JSON serialization (User -> RefreshToken -> User...)
    @OneToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", unique = true)
    private User user;  // UserID will be the col in DB, ID is the col of User entity.

}
