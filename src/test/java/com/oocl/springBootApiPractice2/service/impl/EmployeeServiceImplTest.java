package com.oocl.springBootApiPractice2.service.impl;

import com.oocl.springBootApiPractice2.SpringBootApiPractice2Application;
import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.service.EmployeeService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dylan Wei
 * @date 2018-07-24 17:51
 */
public class EmployeeServiceImplTest {
    private List<Employee> employeesList;

    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp(){
        this.employeesList = new ArrayList<>();
        this.employeesList.add(new Employee(1, "小红", 19, "女", 5000.0, 1));
        this.employeesList.add(new Employee(2, "小智", 15, "男", 5000.0, 1));
        this.employeesList.add(new Employee(3, "小刚", 16, "男", 5000.0, 2));
        this.employeeService = new EmployeeServiceImpl(this.employeesList);
    }

    @Test
    public void should_get_all_employees() {
        List<Employee> resultList = this.employeeService.getAllEmployees();

        assertThat(resultList, is(this.employeesList));
    }

    @Test
    public void should_add_employee_to_list(){
        Employee employee = new Employee();

        boolean succeeded = this.employeeService.addEmployee(employee);

        assertThat(succeeded, is(true));
        assertThat(this.employeesList.size(), is(4));
    }

    @Test
    public void should_update_employee_when_given_valid_new_employee(){
        Employee employee = new Employee(2, "小李", 15, "男", 5000.0, 1);

        boolean succeeded = this.employeeService.updateEmployee(employee);

        assertThat(succeeded, is(true));
        assertThat(this.employeesList.get(1).getName(), equalTo("小李"));
    }

    @Test
    public void should_remove_employee_successfully_when_given_valid_id(){
        boolean succeeded = this.employeeService.removeEmployee(1);

        assertThat(succeeded, is(true));
        assertThat(this.employeesList.size(), is(2));
    }

    @Test
    public void should_get_employees_paging(){
        List<Employee> resultList = this.employeeService.getEmployeePaging(2, 2);

        assertThat(resultList.size(), is(1));
        assertThat(resultList.get(0), equalTo(this.employeesList.get(2)));
    }


}