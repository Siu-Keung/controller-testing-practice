package com.oocl.springBootApiPractice2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springBootApiPractice2.entity.Employee;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

}