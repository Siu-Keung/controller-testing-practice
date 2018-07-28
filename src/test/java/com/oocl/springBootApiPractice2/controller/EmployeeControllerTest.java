package com.oocl.springBootApiPractice2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.exception.exceptionModel.IllegalCommandException;
import com.oocl.springBootApiPractice2.exception.exceptionModel.ResourceNotFoundException;
import com.oocl.springBootApiPractice2.service.EmployeeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Dylan Wei
 * @date 2018-07-28 21:37
 */
@WebMvcTest(EmployeeController.class)
@RunWith(SpringRunner.class)
public class EmployeeControllerTest {
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;
    @Rule
    public ExpectedException expectedException;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Employee> employees;

    @Before
    public void inti(){
        employees = new ArrayList<>();
        employees.add(new Employee(1, "小红", 19, "女", 5000.0, 1));
        employees.add(new Employee(2, "小智", 15, "男", 5000.0, 1));
        employees.add(new Employee(3, "小刚", 16, "男", 5000.0, 1));
        employees.add(new Employee(4, "小霞", 15, "女", 5000.0, 1));
    }

    @Test
    public void should_get_all_employees() throws Exception {
        when(this.employeeService.getAllEmployees())
                .thenReturn(this.employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(this.employees)));
    }

    @Test
    public void should_get_specific_employee_given_valid_id() throws Exception {
        Employee employee = new Employee();

        when(this.employeeService.getEmployeeById(anyInt()))
                .thenReturn(employee);

        mockMvc.perform(get("/employees/" + anyInt()))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(employee)));
    }

    @Test
    public void should_get_male_employees_given_specific_gender() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(2, "小智", 15, "男", 5000.0, 1));
        employees.add(new Employee(3, "小刚", 16, "男", 5000.0, 1));
        when(this.employeeService.getEmployeesByGender(anyString()))
                .thenReturn(employees);

        mockMvc.perform(get("/employees/男"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(employees)));
    }

    @Test
    public void should_return_404_when_id_invalid() throws Exception {
        ResourceNotFoundException exception =
                new ResourceNotFoundException();

        when(this.employeeService.getEmployeeById(anyInt()))
                .thenThrow(exception);

        mockMvc.perform(get("/employees/" + anyInt()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()))
                .andDo(print());
    }

    @Test
    public void should_return_400_when_gender_invalid() throws Exception {
        IllegalCommandException exception =
                new IllegalCommandException();

        when(this.employeeService.getEmployeesByGender(anyString()))
                .thenThrow(exception);

        mockMvc.perform(get("/employees/错误性别"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(exception.getMessage()))
                .andDo(print());
    }

    @Test
    public void should_get_employees_paging_given_valid_page_params() throws Exception {
        when(this.employeeService.getEmployeePaging(anyInt(), anyInt()))
                .thenReturn(this.employees);

        mockMvc.perform(get("/employees/page/9999/pageSize/9999"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(employees)));
    }

    @Test
    public void should_return_404_when_page_params_out_of_range() throws Exception {
        ResourceNotFoundException exception =
                new ResourceNotFoundException();

        doThrow(exception).when(this.employeeService)
                .getEmployeePaging(anyInt(), anyInt());

        mockMvc.perform(get("/employees/page/9999/pageSize/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));
    }






}