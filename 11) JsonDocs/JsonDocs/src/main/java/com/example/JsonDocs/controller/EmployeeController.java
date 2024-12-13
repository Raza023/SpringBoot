package com.example.JsonDocs.controller;

import com.example.JsonDocs.model.Employee;
import com.example.JsonDocs.service.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //@PostMapping("/save")   //JsonDocs does not support it.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public List<Employee> deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }


}
