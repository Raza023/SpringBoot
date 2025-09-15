package com.example.keycloak.service;

import com.example.keycloak.entity.Employee;
import com.example.keycloak.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    public void saveSomeEmployeesOnStartUp() {
        employeeRepository.saveAllAndFlush(Stream.of(
                        new Employee("Hassan Raza", 200000),
                        new Employee("Ali Raza", 250000))
                .collect(Collectors.toList()));
    }

    public Employee getEmployee(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
