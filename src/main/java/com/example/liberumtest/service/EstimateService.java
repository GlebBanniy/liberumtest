package com.example.liberumtest.service;

import com.example.liberumtest.models.Estimate;
import com.example.liberumtest.repo.EstimateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimateService {

    private final EstimateRepo estimateRepo;

    @Autowired
    public EstimateService(EstimateRepo estimateRepo) {
        this.estimateRepo = estimateRepo;
    }

    public void addEstimate(String name) {
        if (estimateRepo.findEstimateByEstimateName(name) == null)
            estimateRepo.save(
                    Estimate.builder()
                            .estimateName(name)
                            .build()
            );
    }
}
