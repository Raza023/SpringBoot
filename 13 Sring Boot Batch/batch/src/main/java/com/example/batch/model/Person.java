package com.example.batch.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "bank_users")
@ToString
public class Person {

    @Id
    private String id;
    private String name;
    private String email;
    private String contactNo;
    private Date dob;
    private String status;
    private String outstandingAmount;
    private Date lastDueDate;
}
