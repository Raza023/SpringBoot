package com.example.hibernate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    //@JsonFormat is used to get date in 21/03/2020 format in postman RequestBody.
    //and it also shows the in 21/03/2020 format when calling get api on postman.
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dob;

}
