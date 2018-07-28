package com.oocl.springBootApiPractice2.service.impl;

import com.oocl.springBootApiPractice2.entity.Company;
import com.oocl.springBootApiPractice2.entity.Employee;
import com.oocl.springBootApiPractice2.exception.exceptionModel.DuplicateResourceIDException;
import com.oocl.springBootApiPractice2.exception.exceptionModel.ResourceNotFoundException;
import com.oocl.springBootApiPractice2.model.CompanyModel;
import com.oocl.springBootApiPractice2.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Dylan Wei
 * @date 2018-07-24 20:13
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    private List<Company> allCompanies;
    private List<Employee> allEmployees;

    @Autowired
    public CompanyServiceImpl(List<Company> allCompanies, List<Employee> allEmployees) {
        this.allCompanies = allCompanies;
        this.allEmployees = allEmployees;
    }

    @Override
    public List<CompanyModel> getAllCompaniesModels() {
        List<CompanyModel> resultList = new ArrayList<>();
        for (Company company : this.allCompanies) {
            List<Employee> employeeList = this.findEmployeesByCompanyId(company.getId());
            resultList.add(new CompanyModel(company, employeeList));
        }
        return resultList;
    }

    private List<Employee> findEmployeesByCompanyId(Integer companyId) {
        return this.allEmployees.stream()
                .filter(item -> item.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyModel getCompanyModelById(Integer id) {
        Optional<Company> optional = this.allCompanies.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
        if (!optional.isPresent())
            throw new ResourceNotFoundException();
        CompanyModel companyModel = new CompanyModel(optional.get(), findEmployeesByCompanyId(id));
        return companyModel;
    }

    @Override
    public List<CompanyModel> getCompaniesModelsPaging(int pageNum, int size) throws IndexOutOfBoundsException {
        int startIndex = (pageNum - 1) * size;
        int endIndex = startIndex + size;
        return this.getAllCompaniesModels().subList(startIndex, endIndex);
    }

    @Override
    public void addCompany(Company newCompany) throws DuplicateResourceIDException {
        if (this.allCompanies.indexOf(newCompany) != -1)
            throw new DuplicateResourceIDException();
        this.allCompanies.add(newCompany);
    }

    @Override
    public void updateCompany(Company newCompany) throws ResourceNotFoundException {
        Optional<Company> optional = this.allCompanies.stream()
                .filter(item -> item.equals(newCompany)).findFirst();
        if (!optional.isPresent())
            throw new ResourceNotFoundException();
        Company targetCompany = optional.get();
        targetCompany.setCompanyName(newCompany.getCompanyName());
    }

    @Override
    public void removeCompanyAndEmployees(Integer companyId) throws ResourceNotFoundException {
        Optional<Company> optional = this.allCompanies.stream()
                .filter(item -> item.getId().equals(companyId)).findFirst();
        if (!optional.isPresent())
            throw new ResourceNotFoundException();
        Company targetCompany = optional.get();
        Iterator<Employee> iterator = this.allEmployees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getCompanyId().equals(targetCompany.getId()))
                iterator.remove();
        }
        this.allCompanies.remove(targetCompany);
    }
}
