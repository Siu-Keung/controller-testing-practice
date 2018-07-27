package com.oocl.springBootApiPractice2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springBootApiPractice2.entity.Company;
import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.model.CompanyModel;
import com.oocl.springBootApiPractice2.service.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Dylan Wei
 * @date 2018-07-27 15:33
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {
    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mockMvc;

    private List<CompanyModel> companies;
    private List<Employee> employeeList;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        this.mapper = new ObjectMapper();

        this.companies = new ArrayList();
        this.companies.add(new CompanyModel(new Company(1, "公司1")));
        this.companies.add(new CompanyModel(new Company(2, "公司2")));

        this.employeeList = new ArrayList<>();
        this.employeeList.add(new Employee(1, "小红", 19, "女", 5000.0, 1));
        this.employeeList.add(new Employee(2, "小智", 15, "男", 5000.0, 1));
        this.employeeList.add(new Employee(7, "小光", 16, "男", 5000.0, 2));
    }

    @Test
    public void should_get_all_companies() throws Exception {
        when(companyService.getAllCompaniesModels()).thenReturn(this.companies);

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(mapper.writeValueAsString(this.companies)));
    }

    @Test
    public void should_get_the_specific_company() throws Exception {
        when(companyService.getCompanyModelById(1)).thenReturn(this.companies.get(0));

        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(mapper.writeValueAsString(this.companies.get(0))));
    }

    @Test
    public void should_get_all_employees_of_the_specific_company() throws Exception {
        CompanyModel companyModel = mock(CompanyModel.class);

        when(companyModel.getEmployees()).thenReturn(this.employeeList);
        when(this.companyService.getCompanyModelById(1)).thenReturn(companyModel);

        mockMvc.perform(get("/companies/1/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(mapper.writeValueAsString(this.employeeList)))
                .andDo(print());
    }


}