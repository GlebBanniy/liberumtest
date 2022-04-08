package com.example.liberumtest.service;

import com.example.liberumtest.dto.DataDto;
import com.example.liberumtest.models.Content;
import com.example.liberumtest.models.Data;
import com.example.liberumtest.models.Estimate;
import com.example.liberumtest.repo.ContentRepo;
import com.example.liberumtest.repo.DataRepo;
import com.example.liberumtest.repo.EstimateRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataServiceTest {

    @Mock
    private DataRepo dataRepo;
    @Mock
    private EstimateRepo estimateRepo;
    @Mock
    private ContentRepo contentRepo;
    private DataService dataService;
    private AutoCloseable autoCloseable;
    private Data testData;
    private Estimate testEstimate;
    private Content testContest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        dataService = new DataService(dataRepo, estimateRepo, contentRepo);
        testEstimate = Estimate.builder()
                .id(1L)
                .estimateName("estimate")
                .build();
        testContest = Content.builder()
                .id(1L)
                .contentName("content")
                .build();
        testData = Data.builder()
                .data_id(1L)
                .company("company")
                .estimate(testEstimate)
                .content(testContest)
                .date("date")
                .value(10)
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    private Data captureFinalVariable() {
        var contentArgumentCaptor = ArgumentCaptor.forClass(Data.class);
        verify(dataRepo).save(contentArgumentCaptor.capture());
        return contentArgumentCaptor.getValue();
    }

    @Test
    void addData() {
        when(estimateRepo.getById(any(Long.class))).thenReturn(testEstimate);
        when(contentRepo.getById(any(Long.class))).thenReturn(testContest);
        DataDto dataDto = new DataDto(1L, "company", 1L, 1L, "date", 10);
        dataService.addData(dataDto);
        var value = captureFinalVariable();
        assertThat(value).isEqualTo(testData);
    }

    @Test
    void getTotal() {
        List<Integer> list = new ArrayList<>(2);
        list.add(10);
        list.add(15);
        when(dataRepo.getTotals(any(Long.class), any(Long.class), any(String.class))).thenReturn(list);
        var result = dataService.getTotal(1L, 1L, "name");
        assertThat(result).isEqualTo(list);
    }

    @Test
    void getCompanies() {
        List<String> list = new ArrayList<>(2);
        list.add("company1");
        list.add("company2");
        when(dataRepo.getCompanies()).thenReturn(list);
        var result = dataService.getCompanies();
        assertThat(result).isEqualTo(list);
    }
}