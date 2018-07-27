package com.oocl.springBootApiPractice2.model;

import com.oocl.springBootApiPractice2.entity.Company;
import com.oocl.springBootApiPractice2.entity.Employee;

import java.util.List;
import java.util.Objects;

/**
 * @author Dylan Wei
 * @date 2018-07-24 20:31
 */
public class CompanyModel extends Company {
    private List<Employee> employees;

    public CompanyModel(Company company){
        super(company.getId(), company.getCompanyName());
    }

    public CompanyModel(Company company, List<Employee> employees) {
        super(company.getId(), company.getCompanyName());
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


}
