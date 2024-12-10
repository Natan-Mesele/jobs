package com.example.jobs.service;

import com.example.jobs.model.SavedJob;

import java.util.List;

public interface SavedJobService {

    List<SavedJob> getSavedJobs(Long userId);

    boolean saveJob(Long userId, Long jobId);

    boolean unsaveJob(Long userId, Long jobId);
}
