package com.example.spring.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class News {

    @Id
    private Long NewsID;

    private String NewsType;
    private String NewsCategory;

}
