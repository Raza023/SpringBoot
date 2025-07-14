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
     * Update employee with only provided fields (partial update).
     *
     * @param id              employee id
     * @param updatedEmployee employee object with fields to update
     * @return updated Employee or null if not found
     */
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee existingEmployee = employeeDataService.findById(id).orElse(null);
        if (existingEmployee == null) {
            return null;
        }
        // Only update fields that are not null (or not zero for primitives)
        if (updatedEmployee.getName() != null) {
            existingEmployee.setName(updatedEmployee.getName());
        }
        if (updatedEmployee.getDept() != null) {
            existingEmployee.setDept(updatedEmployee.getDept());
        }
        // For salary, update only if provided value is greater than zero
        if (updatedEmployee.getSalary() > 0) {
            existingEmployee.setSalary(updatedEmployee.getSalary());
        }
        employeeDataService.saveAndFlush(existingEmployee);
        return existingEmployee;
    }

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
