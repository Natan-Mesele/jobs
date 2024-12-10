package com.example.jobs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob// For a larger description
    private String description;

    @Lob
    private String aboutJob;

    @Lob
    private String keyDutiesAndResponsibilities;

    private String location;

    private String companyName;

    private String jobType; // Full-time, Part-time, Remote, etc.

    private String category;
    @Lob
    private String educationalRequirements;
    @Lob
    private String workExperience;

    @Lob
    private String requiredSkills;

    @Lob
    private String howToApply;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Assuming jobs are linked to a User entity
}



