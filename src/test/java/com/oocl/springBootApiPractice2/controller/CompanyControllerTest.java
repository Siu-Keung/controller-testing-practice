package com.oocl.springBootApiPractice2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springBootApiPractice2.entity.Company;
import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.exception.exceptionModel.DuplicateResourceIDException;
import com.oocl.springBootApiPractice2.exception.exceptionModel.ResourceNotFoundException;
import com.oocl.springBootApiPractice2.model.CompanyModel;
import com.oocl.springBootApiPractice2.service.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
 * @date 2018-07-27 15:33
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {
    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mockMvc;

    private List<CompanyModel> companyModels;
    private List<Employee> employeeList;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        this.mapper = new ObjectMapper();

        this.companyModels = new ArrayList();
        this.companyModels.add(new CompanyModel(new Company(1, "公司1")));
        this.companyModels.add(new CompanyModel(new Company(2, "公司2")));

        this.employeeList = new ArrayList<>();
        this.employeeList.add(new Employee(1, "小红", 19, "女", 5000.0, 1));
        this.employeeList.add(new Employee(2, "小智", 15, "男", 5000.0, 1));
        this.employeeList.add(new Employee(7, "小光", 16, "男", 5000.0, 2));
    }

    @Test
    public void should_get_all_companies() throws Exception {
        when(companyService.getAllCompaniesModels()).thenReturn(this.companyModels);

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(mapper.writeValueAsString(this.companyModels)));
    }

    @Test
    public void should_get_the_specific_company() throws Exception {
        when(companyService.getCompanyModelById(1)).thenReturn(this.companyModels.get(0));

        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(mapper.writeValueAsString(this.companyModels.get(0))));
    }

    @Test
    public void should_get_404_when_given_invalid_company_id() throws Exception {
        ResourceNotFoundException exception =
                new ResourceNotFoundException();

        when(companyService.getCompanyModelById(any())).thenThrow(exception);

        mockMvc.perform(get("/companies/" + anyInt()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));
    }

    @Test
    public void should_get_all_employees_of_the_specific_company() throws Exception {
        CompanyModel companyModel = mock(CompanyModel.class);

        when(companyModel.getEmployees()).thenReturn(this.employeeList);
        when(this.companyService.getCompanyModelById(1)).thenReturn(companyModel);

        mockMvc.perform(get("/companies/1/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(mapper.writeValueAsString(this.employeeList)));
    }

    @Test
    public void should_return_companies_paging() throws Exception {
        List<CompanyModel> expectedList1 = this.companyModels.subList(1, 2);
        List<CompanyModel> expectedList2 = this.companyModels;

        when(this.companyService.getCompaniesModelsPaging(2, 1))
                .thenReturn(this.companyModels.subList(1, 2));
        when(this.companyService.getCompaniesModelsPaging(1, 2))
                .thenReturn(this.companyModels);

        mockMvc.perform(get("/companies/page/2/pageSize/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(this.mapper.writeValueAsString(expectedList1)));

        mockMvc.perform(get("/companies/page/1/pageSize/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(mapper.writeValueAsString(expectedList2)));
    }

    public void should_return_404_when_page_index_out_of_range() throws Exception {
        IndexOutOfBoundsException exception =
                new IndexOutOfBoundsException();

        when(this.companyService.getCompaniesModelsPaging(any(), any())).thenThrow(exception);

        mockMvc.perform(get("/companies/page/1/pageSize/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));
    }

    @Test
    public void should_add_company_successfully_when_given_id_not_exists() throws Exception {
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(anyString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_add_company_failed_when_given_id_already_exists() throws Exception {
        DuplicateResourceIDException exception =
                new DuplicateResourceIDException();

        doThrow(exception).when(this.companyService).addCompany(any());

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(anyString()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void should_return_succeeded_when_modify_successfully() throws Exception {
        mockMvc.perform(put("/companies/2").content(anyString()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(content().string(""));
    }

    @Test
    public void should_return_failed_when_modify_not_successfully() throws Exception {
        ResourceNotFoundException exception =
                new ResourceNotFoundException();

        doThrow(exception).when(this.companyService).updateCompany(any());

        mockMvc.perform(put("/companies/2").content(anyString()))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(content().string(exception.getMessage()));
    }

    @Test
    public void should_remove_company_successfully() throws Exception {
        mockMvc.perform(delete("/companies/99999"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_remove_company_failed() throws Exception {
        ResourceNotFoundException exception =
                new ResourceNotFoundException();

        doThrow(exception).when(this.companyService).removeCompanyAndEmployees(anyInt());

        mockMvc.perform(delete("/companies/9999"))
                .andExpect(status().isNotFound());
    }

}