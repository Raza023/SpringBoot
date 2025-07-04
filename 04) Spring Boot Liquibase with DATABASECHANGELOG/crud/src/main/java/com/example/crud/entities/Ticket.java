package com.example.crud.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket {

    @Id
    @GeneratedValue
    private int id;
    private double amount;
    private String category;


}
