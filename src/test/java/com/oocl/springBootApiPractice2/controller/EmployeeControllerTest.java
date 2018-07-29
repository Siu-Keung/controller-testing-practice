package com.oocl.springBootApiPractice2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.exception.DuplicateResourceIDException;
import com.oocl.springBootApiPractice2.exception.IllegalCommandException;
import com.oocl.springBootApiPractice2.exception.ResourceNotFoundException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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

        // TODO: 2018-07-29 此处controller方法接收到的参数("男")会出现乱码，导致测试不通过，待解决。
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
                .andExpect(content().string(exception.getMessage()));
    }

    @Test
    public void should_return_400_when_gender_invalid() throws Exception {
        IllegalCommandException exception =
                new IllegalCommandException();

        when(this.employeeService.getEmployeesByGender(anyString()))
                .thenThrow(exception);

        mockMvc.perform(get("/employees/错误性别"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(exception.getMessage()));
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

    @Test
    public void should_add_employee_succeessfully_and_return_201() throws Exception {
        mockMvc.perform(post("/employees"))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_add_employee_failed_and_return_403_when_id_exists() throws Exception {
        DuplicateResourceIDException exception =
                new DuplicateResourceIDException();

        doThrow(exception).when(this.employeeService)
                .addEmployee(any(Employee.class));

        mockMvc.perform(post("/employees"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(exception.getMessage()));
    }

    @Test
    public void should_update_employee_successfully_when_given_id_exists() throws Exception {
        mockMvc.perform(put("/employees/" + anyInt()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void should_update_employee_failed_given_id_not_exists() throws Exception {
        ResourceNotFoundException exception =
                new ResourceNotFoundException();

        doThrow(exception).when(this.employeeService)
                .updateEmployee(any(Employee.class));

        mockMvc.perform(put("/employees/" + anyInt()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));
    }

    @Test
    public void should_remove_employee_successfully() throws Exception {
        mockMvc.perform(delete("/employees/" + anyInt()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void should_remove_employee_failed_given_id_not_exists() throws Exception {
        ResourceNotFoundException exception =
                new ResourceNotFoundException();

        doThrow(exception).when(this.employeeService)
                .removeEmployee(anyInt());

        mockMvc.perform(delete("/employees/" + anyInt()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));
    }






}