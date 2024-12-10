package com.example.jobs.controller;

import com.example.jobs.model.User;
import com.example.jobs.model.UserProfile;
import com.example.jobs.service.UserProfileService;
import com.example.jobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserProfile> createProfile(
            @RequestBody UserProfile profile,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        // Find the authenticated user using the JWT
        User user = userService.findUserByJwtToken(jwt);

        // Check if the user already has a profile
        UserProfile existingProfile = userProfileService.findByUser(user);
        if (existingProfile != null) {
            throw new RuntimeException("Profile already exists for this user");
        }

        // Associate the profile with the user
        profile.setUser(user);

        // Save the profile
        UserProfile savedProfile = userProfileService.saveProfile(profile);

        return ResponseEntity.ok(savedProfile);
    }
}
