package com.example.jobs.service;

import com.example.jobs.model.Job;

import java.util.List;

public interface JobService {
    Job createJob(Job job);
    Job getJobById(Long id);
    List<Job> getAllJobs();  // For unauthenticated users

    List<Job> getJobsForUser(String email);  // For authenticated users based on email
    Job updateJob(Long id, Job job);
    void deleteJob(Long id);
}
