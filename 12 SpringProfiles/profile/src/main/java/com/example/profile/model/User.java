package com.example.profile.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "[user]")
public class User {
    @Id
    private Integer ID;
    private String name;
}
