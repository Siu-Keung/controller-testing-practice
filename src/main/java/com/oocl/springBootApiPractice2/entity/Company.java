package com.oocl.springBootApiPractice2.entity;

import java.util.List;
import java.util.Objects;

/**
 * @author Dylan Wei
 * @date 2018-07-24 20:10
 */
public class Company {
    private Integer id;
    private String companyName;

    public Company(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Company(Integer id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public Company(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(getId(), company.getId());
    }

}
