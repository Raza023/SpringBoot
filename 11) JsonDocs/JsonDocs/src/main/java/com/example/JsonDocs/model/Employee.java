package com.example.JsonDocs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ApiObject(show = false)
@ApiObject(description = "Employee Entity", name = "Employee (entity)")
public class Employee {

    @Id
    //@GeneratedValue // we can use it as well here.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiObjectField(name = "employee id", description = "Auto generated id")
    private int id;
    @ApiObjectField(name = "employee name", description = "name same as voterId")
    private String name;
    @ApiObjectField(name = "department", description = "dept section")
    private String dept;
    @ApiObjectField(name = "salary", description = "annual income")
    private double salary;

}
