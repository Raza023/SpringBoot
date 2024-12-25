package com.example.JsonDocs.controller;

import com.example.JsonDocs.model.Employee;
import com.example.JsonDocs.service.EmployeeService;
import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/employee")
@Api(name = "Employee Management System", description = "Employee info",
        group = "Management", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //@PostMapping("/save")   //JsonDocs does not support it.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiMethod(description = "add new employee")
    public String saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "find employee by id", path = { "id" })
    public Employee getEmployee(@PathVariable @ApiPathParam(description = "input employee id to get.", name = "id") int id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiMethod(description = "delete employee by id", path = { "id" })
    public List<Employee> deleteEmployee(@PathVariable @ApiPathParam(description = "input employee id to delete.", name = "id") int id) {
        return employeeService.deleteEmployee(id);
    }


}
