package com.oocl.springBootApiPractice2.controller;

import com.oocl.springBootApiPractice2.entity.Company;
import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.model.CompanyModel;
import com.oocl.springBootApiPractice2.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

/**
 * @author Dylan Wei
 * @date 2018-07-24 22:44
 */
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public List<CompanyModel> getCompanyModels(){
        return companyService.getAllCompaniesModels();
    }

    @GetMapping("/companies/{id}")
    public CompanyModel getCompanyModel(@PathVariable Integer id){
        return companyService.getCompanyModelById(id);
    }

    @GetMapping("/companies/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Integer id){
        return companyService.getCompanyModelById(id).getEmployees();
    }

    @GetMapping("/companies/page/{pageNum}/pageSize/{pageSize}")
    public List<CompanyModel> getCompanyModelsPaging(
            @PathVariable Integer pageNum, @PathVariable Integer pageSize
    ){
        return companyService.getCompaniesModelsPaging(pageNum, pageSize);
    }

    @PostMapping("/companies")
    public String addCompany(Company company){
        return this.companyService.addCompany(company) ? "succeeded" : "failed";
    }

    @PutMapping("/companies/{id}")
    public String updateCompany(Company company){
        return this.companyService.updateCompany(company) ? "succeeded" : "failed";
    }

    @DeleteMapping("/companies/{id}")
    public String removeCompany(@PathVariable Integer id){
        return this.companyService.removeCompanyAndEmployees(id) ? "succeeded" : "failed";
    }


}
