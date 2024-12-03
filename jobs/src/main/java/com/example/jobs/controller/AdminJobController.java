package com.example.jobs.controller;

import com.example.jobs.model.Job;
import com.example.jobs.model.User;
import com.example.jobs.service.JobService;
import com.example.jobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/job")
public class AdminJobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    // Endpoint to create a new job
    @PostMapping
    public ResponseEntity<Job> createJob(
            @RequestBody Job job,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    // Endpoint to update a job
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Long id,
            @RequestBody Job job,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Job updatedJob = jobService.updateJob(id, job);
        if (updatedJob != null) {
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint to delete a job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
