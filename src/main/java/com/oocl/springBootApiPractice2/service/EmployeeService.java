package com.oocl.springBootApiPractice2.service;

import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.exception.DuplicateResourceIDException;
import com.oocl.springBootApiPractice2.exception.ResourceNotFoundException;

import java.util.List;

/**
 * @author Dylan Wei
 * @date 2018-07-24 17:46
 */
public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer id) throws ResourceNotFoundException;

    void addEmployee(Employee employee) throws DuplicateResourceIDException;

    void updateEmployee(Employee employee) throws ResourceNotFoundException;

    void removeEmployee(Integer id) throws ResourceNotFoundException;

    List<Employee> getEmployeePaging(int pageNum, int size);

    List<Employee> getEmployeesByGender(String gender);
}
