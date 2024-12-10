package com.example.jobs.repository;

import com.example.jobs.model.Job;
import com.example.jobs.model.SavedJob;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    @EntityGraph(attributePaths = {"job"})
    List<SavedJob> findByUserId(Long userId);

    Optional<SavedJob> findByUserIdAndJobId(Long userId, Long jobId);
}
