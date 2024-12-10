package com.example.jobs.service;

import com.example.jobs.model.Job;
import com.example.jobs.model.SavedJob;
import com.example.jobs.repository.JobRepository;
import com.example.jobs.repository.SavedJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SavedJobServiceImpl implements SavedJobService {

    @Autowired
    private SavedJobRepository savedJobRepository;

    @Autowired
    private JobRepository jobRepository;  // Inject JobRepository to get full job details

    @Override
    public List<SavedJob> getSavedJobs(Long userId) {
        // Fetch saved jobs by userId
        List<SavedJob> savedJobs = savedJobRepository.findByUserId(userId);

        // Return saved jobs (the job details are already included because of the @ManyToOne relationship)
        return savedJobs;
    }

    @Override
    public boolean saveJob(Long userId, Long jobId) {
        if (userId == null || jobId == null) {
            throw new IllegalArgumentException("User ID and Job ID must not be null");
        }

        Optional<SavedJob> existingSavedJob = savedJobRepository.findByUserIdAndJobId(userId, jobId);
        if (!existingSavedJob.isPresent()) {
            SavedJob savedJob = new SavedJob();
            savedJob.setUserId(userId);

            Optional<Job> job = jobRepository.findById(jobId); // Use JobRepository here
            if (job.isPresent()) {
                savedJob.setJob(job.get());
            } else {
                System.out.println("Job with ID " + jobId + " not found.");
                return false; // Cannot save without a valid job
            }

            savedJobRepository.save(savedJob);
            return true;
        }
        return false;
    }

    @Override
    public boolean unsaveJob(Long userId, Long jobId) {
        // Find the saved job by userId and jobId
        Optional<SavedJob> savedJob = savedJobRepository.findByUserIdAndJobId(userId, jobId);
        if (savedJob.isPresent()) {
            savedJobRepository.delete(savedJob.get());  // Remove the saved job
            return true;
        }
        return false;
    }
}
