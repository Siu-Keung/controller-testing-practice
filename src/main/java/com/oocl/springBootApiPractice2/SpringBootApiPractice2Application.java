package com.oocl.springBootApiPractice2;

import com.oocl.springBootApiPractice2.entity.Company;
import com.oocl.springBootApiPractice2.entity.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootApiPractice2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApiPractice2Application.class, args);
	}

	@Bean
	public List<Employee> allEmployees(){
        List<Employee> employeesList = new ArrayList<>();
        employeesList.add(new Employee(1, "小红", 19, "女", 5000.0, 1));
        employeesList.add(new Employee(2, "小智", 15, "男", 5000.0, 1));
        employeesList.add(new Employee(3, "小刚", 16, "男", 5000.0, 1));
        employeesList.add(new Employee(4, "小霞", 15, "女", 5000.0, 1));
        employeesList.add(new Employee(5, "小青", 19, "女", 5000.0, 2));
        employeesList.add(new Employee(6, "小李", 15, "男", 5000.0, 2));
        employeesList.add(new Employee(7, "小光", 16, "男", 5000.0, 2));
        return employeesList;
	}

	@Bean
    public List<Company> allCompanies(){
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "公司1"));
        companyList.add(new Company(2, "公司2"));
        return companyList;
    }
}
