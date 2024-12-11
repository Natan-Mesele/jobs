package com.example.jobs.repository;

import com.example.jobs.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
    boolean existsByUserIdAndJobId(Long userId, Long jobId);
}
