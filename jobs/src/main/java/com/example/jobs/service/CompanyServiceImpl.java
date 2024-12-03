package com.example.jobs.service;

import com.example.jobs.model.Company;
import com.example.jobs.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Company existingCompany = getCompanyById(id);
        existingCompany.setName(company.getName());
        existingCompany.setDescription(company.getDescription());
        existingCompany.setLogoUrl(company.getLogoUrl());
        return companyRepository.save(existingCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = getCompanyById(id);
        companyRepository.delete(company);
    }
}
