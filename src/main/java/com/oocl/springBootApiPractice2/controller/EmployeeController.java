package com.oocl.springBootApiPractice2.controller;

import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.exception.IllegalCommandException;
import com.oocl.springBootApiPractice2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            if(!param.equals("男") && !param.equals("女"))
                throw new IllegalCommandException();
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
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(Employee newEmployee){
        this.employeeService.addEmployee(newEmployee);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(Employee newEmployee){
        this.employeeService.updateEmployee(newEmployee);
    }

    @DeleteMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer employeeId){
        this.employeeService.removeEmployee(employeeId);
    }
}
