package com.example.jsonpath.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CompanyResponseDto {

    private String company;
    private long id;
    private boolean isActive;
    private long established;
    private Address address;
    private List<Department> departments;
    private Meta meta;

}
