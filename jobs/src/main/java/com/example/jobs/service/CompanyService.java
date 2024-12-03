package com.example.jobs.service;

import com.example.jobs.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    Company createCompany(Company company);
    Company updateCompany(Long id, Company company);
    void deleteCompany(Long id);
}
