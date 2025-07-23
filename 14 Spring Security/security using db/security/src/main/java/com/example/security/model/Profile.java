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
import lombok.Data;

@Data
@Entity
@Table(name = "Profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProfileID")
    private Long profileId;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Address")
    private String address;

    @JsonIgnore // Prevent recursion during JSON serialization (User -> Profile -> User...)
    @OneToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", unique = true)
    private User user;
}
