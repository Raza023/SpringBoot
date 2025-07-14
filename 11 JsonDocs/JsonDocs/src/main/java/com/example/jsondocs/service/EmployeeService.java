package com.example.jsondocs.service;

import com.example.jsondocs.dao.EmployeeDataService;
import com.example.jsondocs.model.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDataService employeeDataService;

    /**
     * Save new employee.
     *
     * @param employee employee
     * @return String
     */
    public String saveEmployee(Employee employee) {
        Employee savedEmployee = employeeDataService.saveAndFlush(employee);
        return "New employee added with id: " + savedEmployee.getId();
    }

    /**
     * Get employees.
     *
     * @return Employee
     */
    public List<Employee> getEmployees() {
        return employeeDataService.findAll();
    }

    /**
     * Get employee based on id.
     *
     * @param id id
     * @return Employee
     */
    public Employee getEmployee(int id) {
        return employeeDataService.findById(id).orElse(null);
    }

    /**
     * Delete the employee and fetch remaining employees.
     *
     * @param id id
     * @return list of Employees
     */
    public List<Employee> deleteEmployee(int id) {
        employeeDataService.deleteById(id);
        employeeDataService.flush();
        return employeeDataService.findAll();
    }


}
