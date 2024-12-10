package com.example.jobs.service;

import com.example.jobs.model.User;
import com.example.jobs.model.UserProfile;
import com.example.jobs.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile findByUser(User user) {
        return userProfileRepository.findByUser(user);  // Assumes that 'User' is associated with 'UserProfile'
    }

    public UserProfile saveProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

}
