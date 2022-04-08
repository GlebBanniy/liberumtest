package com.example.liberumtest.repo;

import com.example.liberumtest.models.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateRepo extends JpaRepository<Estimate, Long> {
    Estimate findEstimateByEstimateName(String name);
}
