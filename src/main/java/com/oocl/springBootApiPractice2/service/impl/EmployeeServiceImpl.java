package com.oocl.springBootApiPractice2.service.impl;

import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Dylan Wei
 * @date 2018-07-24 17:50
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private List<Employee> allEmployees;

    @Autowired
    public EmployeeServiceImpl(List<Employee> allEmployees){
        this.allEmployees = allEmployees;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.allEmployees;
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Optional<Employee> optional = this.allEmployees.stream().filter(item -> item.getId().equals(id)).findFirst();
        if(optional.isPresent())
            return optional.get();
        else
            return null;
    }

    @Override
    public Boolean addEmployee(Employee employee) {
        if(this.allEmployees.indexOf(employee) != -1)
            return false;
        this.allEmployees.add(employee);
        return true;
    }

    @Override
    public Boolean updateEmployee(Employee employee) {
        int index = this.allEmployees.indexOf(employee);
        if(-1 == index)
            return false;
        Employee targetEmployee = this.allEmployees.get(index);
        targetEmployee.setName(employee.getName());
        targetEmployee.setAge(employee.getAge());
        targetEmployee.setGender(employee.getGender());
        return true;
    }

    @Override
    public Boolean removeEmployee(Integer id) {
        return this.allEmployees.remove(new Employee(id));
    }

    @Override
    public List<Employee> getEmployeePaging(int pageNum, int size) {
        int startIndex = (pageNum - 1) * size;
        int endIndex = startIndex +  size;
        if(endIndex > this.allEmployees.size())
            endIndex = this.allEmployees.size();
        return this.allEmployees.subList(startIndex, endIndex);
    }

    @Override
    public List<Employee> getEmployeesByGender(String gender) {
        return this.allEmployees.stream().filter(
                item -> item.getGender().equals(gender)).collect(Collectors.toList());
    }
}

