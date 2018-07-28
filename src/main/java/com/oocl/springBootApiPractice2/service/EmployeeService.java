package com.oocl.springBootApiPractice2.service;

import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.exception.exceptionModel.ResourceNotFoundException;

import java.util.List;

/**
 * @author Dylan Wei
 * @date 2018-07-24 17:46
 */
public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer id) throws ResourceNotFoundException;

    Boolean addEmployee(Employee employee);

    Boolean updateEmployee(Employee employee);

    Boolean removeEmployee(Integer id);

    List<Employee> getEmployeePaging(int pageNum, int size);

    List<Employee> getEmployeesByGender(String gender);
}
