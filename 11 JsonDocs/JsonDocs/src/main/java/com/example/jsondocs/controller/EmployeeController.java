package com.example.jsondocs.controller;

import com.example.jsondocs.model.Employee;
import com.example.jsondocs.service.EmployeeService;
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

@RestController
@RequestMapping("/employee")
@Api(name = "Employee Management System", description = "Employee info", group = "Management", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // @PostMapping("/save") //JsonDocs does not support it.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiMethod(description = "add new employee")
    public String saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiMethod(description = "find employee by id", path = { "id" })
    public Employee getEmployee(
            @PathVariable @ApiPathParam(description = "input employee id to get.", name = "id") int id) {
        return employeeService.getEmployee(id);
    }

    /*
     * Endpoint:
     * HEAD /employee/get/{id}
     * 
     * Description:
     * Returns only the headers (no body) for the get employee by id endpoint.
     * Useful for checking if an employee exists and for metadata (like content
     * type, length) without transferring the full resource.
     * 
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.HEAD)
    @ApiMethod(description = "head request for employee by id", path = { "id" })
    public void headGetEmployee(@PathVariable int id) {
        // Spring automatically handles HEAD requests; no body is returned.
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiMethod(description = "find employee by id")
    public List<Employee> getEmployee() {
        return employeeService.getEmployees();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiMethod(description = "delete employee by id", path = { "id" })
    public List<Employee> deleteEmployee(
            @PathVariable @ApiPathParam(description = "input employee id to delete.", name = "id") int id) {
        return employeeService.deleteEmployee(id);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PATCH)
    @ApiMethod(description = "update employee partially by id", path = { "id" })
    public Employee updateEmployee(
            @PathVariable @ApiPathParam(description = "employee id to update", name = "id") int id,
            @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    /*
     * Endpoint:
     * OPTIONS /employee/update/{id}
     * 
     * Description:
     * Returns the allowed HTTP methods and other CORS-related information for the
     * update employee endpoint. Useful for clients to discover what operations are
     * supported.
     */ 
    @RequestMapping(value = "/update/{id}", method = RequestMethod.OPTIONS)
    public void optionsUpdateEmployee() {
        // Spring automatically handles OPTIONS requests for CORS.
    }

}
