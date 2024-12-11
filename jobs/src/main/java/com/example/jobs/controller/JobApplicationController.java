package com.example.jobs.controller;

import com.example.jobs.model.Job;
import com.example.jobs.model.JobApplication;
import com.example.jobs.model.User;
import com.example.jobs.repository.JobApplicationRepository;
import com.example.jobs.repository.JobRepository;
import com.example.jobs.service.JobApplicationService;
import com.example.jobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;  // Inject JobRepository to fetch the job entity

    @Autowired
    private UserService userService;

    @Autowired
    private JobApplicationService jobApplicationService;

    private static final String UPLOAD_DIR = "uploads/resumes/";

    @PostMapping("/{userId}/{jobId}")
    public ResponseEntity<Map<String, Object>> applyForJob(
            @PathVariable Long userId,
            @PathVariable Long jobId,
            @RequestParam("resume") MultipartFile resume,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            // Fetch the user based on the JWT token
            User user = userService.findUserByJwtToken(jwt);

            // Fetch the job details from the database using jobId
            Optional<Job> jobOptional = jobRepository.findById(jobId);
            if (!jobOptional.isPresent()) {
                return ResponseEntity.status(404).body(Map.of("message", "Job not found."));
            }
            Job job = jobOptional.get();  // Get the Job entity

            // Save the resume file
            String fileName = userId + "_" + jobId + "_" + resume.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent()); // Ensure the directory exists
            Files.write(filePath, resume.getBytes());

            // Create a new job application and set its properties
            JobApplication application = new JobApplication();
            application.setUserId(user.getId()); // Set user ID from the JWT token
            application.setJob(job); // Set the full Job entity
            application.setResumePath(filePath.toString()); // Set the resume file path

            // Save the job application to the database
            jobApplicationRepository.save(application);

            return ResponseEntity.ok(Map.of(
                    "message", "Application successful! Resume uploaded.",
                    "userId", user.getId()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Application failed. " + e.getMessage()));
        }
    }

    @GetMapping("/{userId}/applied-jobs")
    public ResponseEntity<List<Job>> getAppliedJobs(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Job> appliedJobs = jobApplicationService.getAppliedJobs(userId);
        return ResponseEntity.ok(appliedJobs);
    }
}
