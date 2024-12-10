package com.example.jobs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String birthday; // Use LocalDate for proper date handling
    private String gender;
    private String profession;
    private String careerLevel;

    // Education fields
    private String universityCollege;
    private String universityMajor;
    private String degreeType;
    private String graduationYear;

    // Work experience fields
    private String companyName;
    private String jobTitle;
    private String jobDescription;
    private String jobStartYear;
    private boolean currentlyWorking;

    @ElementCollection
    @JsonDeserialize(using = CommaSeparatedSkillsDeserializer.class) // Use custom deserializer
    private List<String> skills;

    // Portfolio fields
    private String portfolioLink;
    private String projectLink1;
    private String projectLink2;
    private String projectLink3;
    private String projectLink4;

    // Desired employment
    private String desiredEmployment;
    private String desiredSalary;
    private String currentSalary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
