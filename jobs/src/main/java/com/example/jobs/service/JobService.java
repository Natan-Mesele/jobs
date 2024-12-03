package com.example.jobs.service;

import com.example.jobs.model.Job;

import java.util.List;

public interface JobService {
    Job createJob(Job job);
    Job getJobById(Long id);
    List<Job> getAllJobs();
    Job updateJob(Long id, Job job);
    void deleteJob(Long id);
}
