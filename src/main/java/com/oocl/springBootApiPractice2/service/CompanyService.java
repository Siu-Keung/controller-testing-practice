package com.oocl.springBootApiPractice2.service;

import com.oocl.springBootApiPractice2.entity.Company;
import com.oocl.springBootApiPractice2.exception.exceptionModel.DuplicateResourceIDException;
import com.oocl.springBootApiPractice2.exception.exceptionModel.ResourceNotFoundException;
import com.oocl.springBootApiPractice2.model.CompanyModel;

import java.util.List;

/**
 * @author Dylan Wei
 * @date 2018-07-24 20:13
 */
public interface CompanyService {

    List<CompanyModel> getAllCompaniesModels();

    CompanyModel getCompanyModelById(Integer id);

    List<CompanyModel> getCompaniesModelsPaging(int pageNum, int size) throws IndexOutOfBoundsException;

    void addCompany(Company newCompany) throws DuplicateResourceIDException;

    boolean updateCompany(Company newCompany) throws ResourceNotFoundException;

    void removeCompanyAndEmployees(Integer companyId) throws ResourceNotFoundException;
}
