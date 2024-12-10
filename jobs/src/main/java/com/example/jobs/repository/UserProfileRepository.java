package com.example.jobs.repository;

import com.example.jobs.model.User;
import com.example.jobs.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUser(User user);
}
