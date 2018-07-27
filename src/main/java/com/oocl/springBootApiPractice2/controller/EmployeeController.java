package com.oocl.springBootApiPractice2.controller;

import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Wei
 * @date 2018-07-24 22:13
 */

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{param}")
    public Object getEmployeeById(@PathVariable String param){
        try {
            return this.employeeService.getEmployeeById(Integer.valueOf(param));
        }catch (NumberFormatException e){
            return this.employeeService.getEmployeesByGender(param);
        }
    }

    @GetMapping("/employees/page/{pageNum}/pageSize/{pageSize}")
    public List<Employee> getEmployeesPaging(
            @PathVariable Integer pageNum, @PathVariable Integer pageSize
    ){
        return this.employeeService.getEmployeePaging(pageNum, pageSize);
    }

    @PostMapping("/employees")
    public String addEmployee(Employee newEmployee){
        if(this.employeeService.addEmployee(newEmployee))
            return "succeeded";
        else
            return "failed";
    }

    @PutMapping("/employees/{employeeId}")
    public String updateEmployee(Employee newEmployee){
        if(this.employeeService.updateEmployee(newEmployee))
            return "succeeded";
        else
            return "failed";
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable Integer employeeId){
        if(this.employeeService.removeEmployee(employeeId))
            return "succeeded";
        else
            return "failed";
    }
}
