package com.example.jobs.controller;

import com.example.jobs.model.Job;
import com.example.jobs.model.User;
import com.example.jobs.service.JobService;
import com.example.jobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class UserJobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs(
            @RequestHeader(value = "Authorization", required = false) String jwt
    ) {
        try {
            if (jwt != null && !jwt.isEmpty() && jwt.startsWith("Bearer ")) {
                // Remove the "Bearer " prefix
                jwt = jwt.substring(7);

                // Extract user details from the JWT
                User user = userService.findUserByJwtToken(jwt);
                System.out.println("Authenticated user: " + user.getEmail());

                // Optionally, you can personalize jobs based on the user
                List<Job> personalizedJobs = jobService.getJobsForUser(user.getEmail());
                return new ResponseEntity<>(personalizedJobs, HttpStatus.OK);
            } else {
                System.out.println("Unauthenticated request.");
            }
        } catch (Exception e) {
            System.out.println("Error while processing JWT: " + e.getMessage());
        }

        List<Job> publicJobs = jobService.getAllJobs();
        return new ResponseEntity<>(publicJobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Job job = jobService.getJobById(id);
        if (job != null) {
            return new ResponseEntity<>(job, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
