package com.example.liberumtest.service;

import com.example.liberumtest.models.Estimate;
import com.example.liberumtest.repo.EstimateRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EstimateServiceTest {

    @Mock
    private EstimateRepo estimateRepo;
    private EstimateService estimateService;
    private AutoCloseable autoCloseable;
    private Estimate estimate;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        estimateService = new EstimateService(estimateRepo);
        estimate = Estimate.builder()
                .estimateName("testEstimate")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    private Estimate captureFinalVariable() {
        var contentArgumentCaptor = ArgumentCaptor.forClass(Estimate.class);
        verify(estimateRepo).save(contentArgumentCaptor.capture());
        return contentArgumentCaptor.getValue();
    }

    @Test
    void addEstimate() {
        estimateService.addEstimate("testEstimate");
        var value = captureFinalVariable();
        assertThat(value).isEqualTo(estimate);
    }
}
