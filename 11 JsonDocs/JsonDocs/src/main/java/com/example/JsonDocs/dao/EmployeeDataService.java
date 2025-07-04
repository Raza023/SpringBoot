package com.example.JsonDocs.dao;

import com.example.JsonDocs.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDataService extends JpaRepository<Employee, Integer> {

}
