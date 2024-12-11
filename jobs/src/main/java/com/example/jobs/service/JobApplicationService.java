package com.example.jobs.service;

import com.example.jobs.model.Job;
import com.example.jobs.model.JobApplication;
import com.example.jobs.repository.JobApplicationRepository;
import com.example.jobs.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAppliedJobs(Long userId) {
        List<JobApplication> applications = jobApplicationRepository.findByUserId(userId);
        return applications.stream()
                .map(JobApplication::getJob) // Directly access the Job object
                .collect(Collectors.toList());
    }

    public boolean applyForJob(Long userId, Long jobId) {
        if (jobApplicationRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new IllegalStateException("User has already applied for this job.");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        JobApplication application = new JobApplication();
        application.setUserId(userId);
        application.setJob(job);

        jobApplicationRepository.save(application);
        return true;
    }
}
