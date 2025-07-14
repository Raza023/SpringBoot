package com.example.batch.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Bank Users")
public class Person {

    @Id
    private int id;
    private String name;
    private String email;
    private String contactNo;
    private Date dob;
    private String status;
    private String outstandingAmount;
    private Date lastDueDate;
}
