package com.example.jobs.repository;

import com.example.jobs.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAll();

    // Fetch jobs for a specific user based on email (for authenticated users)
    List<Job> findJobsByUserEmail(String email);

    Optional<Job> findById(Long id);
}
