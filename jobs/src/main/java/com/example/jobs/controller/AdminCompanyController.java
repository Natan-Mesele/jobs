package com.example.jobs.controller;

import com.example.jobs.model.Company;
import com.example.jobs.model.User;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/company")
public class AdminCompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    // Create a new company
    @PostMapping
    public ResponseEntity<Company> createCompany(
            @RequestBody Company company,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(companyService.createCompany(company));
    }

    // Update an existing company
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable Long id,
            @RequestBody Company company,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(companyService.updateCompany(id, company));
    }

    // Delete a company
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Company deleted successfully.");
    }
}
