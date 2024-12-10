package com.example.jobs.service;

import com.example.jobs.model.Job;
import com.example.jobs.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService{

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Job createJob(Job job) {
        Job createJobs = new Job();

        createJobs.setCategory(job.getCategory());
        createJobs.setDescription(job.getDescription());
        createJobs.setLocation(job.getLocation());
        createJobs.setTitle(job.getTitle());
        createJobs.setCompanyName(job.getCompanyName());
        createJobs.setJobType(job.getJobType());

        createJobs.setAboutJob(job.getAboutJob());
        createJobs.setKeyDutiesAndResponsibilities(job.getKeyDutiesAndResponsibilities());
        createJobs.setEducationalRequirements(job.getEducationalRequirements());
        createJobs.setWorkExperience(job.getWorkExperience());
        createJobs.setRequiredSkills(job.getRequiredSkills());
        createJobs.setHowToApply(job.getHowToApply());
        createJobs.setImageUrl(job.getImageUrl());

        return jobRepository.save(createJobs);
    }

    @Override
    public Job getJobById(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        return job.orElse(null);  // Return null or handle as needed
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> getJobsForUser(String email) {
        // Assuming your jobs have a userEmail field or a related User entity
        return jobRepository.findJobsByUserEmail(email);  // Query jobs associated with the user's email
    }

    @Override
    public Job updateJob(Long id, Job job) {
        if (jobRepository.existsById(id)) {
            job.setId(id);  // Assuming Job has a setter for id
            return jobRepository.save(job);
        }
        return null;  // Return null or handle as needed
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
