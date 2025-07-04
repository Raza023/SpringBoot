package com.example.scheduler.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MyUser") // Renamed to avoid conflict
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @Override               //no need to write that line by the way.
    public String toString() {
        return "{User id: "+this.id+"\n"+"User name: "+this.name+"}";
    }

}
