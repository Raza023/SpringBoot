package com.example.jsonpath.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jsonpath.dto.Address;
import com.example.jsonpath.dto.CompanyResponseDto;
import com.example.jsonpath.dto.Department;
import com.example.jsonpath.dto.Employee;
import com.example.jsonpath.dto.Meta;
import com.jayway.jsonpath.JsonPath;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final ObjectMapper objectMapper;

    @GetMapping("/skills")
    public List<String> getCompanyFirstEmployeeSkillList() {
        CompanyResponseDto companyResponseDto = getCompanyInfo();

        String json = objectMapper.writeValueAsString(companyResponseDto);
        //JsonPath.parse(json).set("$.meta.version", 1.3).set("$.meta.lastUpdated", "2026-02-14T12:00:00Z")
        //.set("$.meta.notes", "Updated company info with new version and timestamp");

        List<String> skills = JsonPath.parse(json).read("$.departments[0].employees[0].skills[*]", List.class);

        return skills;
    }

    private CompanyResponseDto getCompanyInfo() {
        CompanyResponseDto companyResponseDto = new CompanyResponseDto();

        companyResponseDto.setCompany("TechCorp Solutions");
        companyResponseDto.setId(1024L);
        companyResponseDto.setActive(true);
        companyResponseDto.setEstablished(2005L);

        Address address = new Address("123 Innovation Way", "Silicon Valley", "CA", "94025");
        companyResponseDto.setAddress(address);

        Employee e1 = new Employee(1L, "Bob Smith", "Senior Developer", Arrays.asList("JavaScript", "Python", "Rust"));
        Employee e2 = new Employee(2L, "Charlie Davis", "DevOps Engineer", Arrays.asList("Docker", "Kubernetes", "AWS"));
        Department engineering = new Department("Engineering", "Alice Johnson", Arrays.asList(e1, e2));

        Employee e3 = new Employee(3L, "Frank White", "Sales Manager", Arrays.asList("Negotiation", "CRM"));
        Department sales = new Department("Sales", "Eva Martinez", Arrays.asList(e3));

        List<Department> departments = Arrays.asList(engineering, sales);
        companyResponseDto.setDepartments(departments);

        Meta meta = new Meta("2026-02-13T10:00:00Z", 1.2, null);
        companyResponseDto.setMeta(meta);
        return companyResponseDto;
    }

}
