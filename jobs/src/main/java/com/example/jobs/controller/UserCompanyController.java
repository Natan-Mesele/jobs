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
@RequestMapping("/api/company")
public class UserCompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Company> getAllCompanies(
            @RequestHeader(value = "Authorization", required = false) String jwt
    ) throws Exception {
        if (jwt != null && !jwt.isEmpty()) {
            try {
                User user = userService.findUserByJwtToken(jwt);
                System.out.println("Authenticated user: " + user.getEmail());
            } catch (Exception e) {
                System.out.println("Invalid or expired JWT token");
            }
        }
        return companyService.getAllCompanies();
    }

    // Get a specific company by ID
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }
}
