package com.example.jsondocs.dao;

import com.example.jsondocs.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDataService extends JpaRepository<Employee, Integer> {

}
