package com.example.jobs.controller;

import com.example.jobs.model.SavedJob;
import com.example.jobs.model.User;
import com.example.jobs.service.SavedJobService;
import com.example.jobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
public class SavedJobController {

    @Autowired
    private SavedJobService savedJobService;

    @Autowired
    private UserService userService;

    // Endpoint to get saved jobs for a user
    @GetMapping("/{userId}")
    public List<SavedJob> getSavedJobs(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid userId");
        }
        User user = userService.findUserByJwtToken(jwt);
        return savedJobService.getSavedJobs(userId);
    }

    // Endpoint to save a job for a user
    @PostMapping("/save/{userId}/{jobId}")
    public boolean saveJob(
            @PathVariable Long userId,
            @PathVariable Long jobId,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return savedJobService.saveJob(userId, jobId);
    }

    @DeleteMapping("/unsave")
    public boolean unsaveJob(
            @RequestParam Long userId,
            @RequestParam Long jobId,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return savedJobService.unsaveJob(userId, jobId);
    }
}
