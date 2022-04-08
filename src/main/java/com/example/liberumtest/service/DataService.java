package com.example.liberumtest.service;

import com.example.liberumtest.dto.DataDto;
import com.example.liberumtest.models.Data;
import com.example.liberumtest.repo.ContentRepo;
import com.example.liberumtest.repo.DataRepo;
import com.example.liberumtest.repo.EstimateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private final DataRepo dataRepo;
    private final EstimateRepo estimateRepo;
    private final ContentRepo contentRepo;

    @Autowired
    public DataService(DataRepo dataRepo, EstimateRepo estimateRepo, ContentRepo contentRepo) {
        this.dataRepo = dataRepo;
        this.estimateRepo = estimateRepo;
        this.contentRepo = contentRepo;
    }

    private Data dtoToEntity(DataDto dataDto) {
        return Data.builder()
                .data_id(dataDto.getDataId())
                .company(dataDto.getCompanyName())
                .estimate(estimateRepo.getById(dataDto.getEstimateId()))
                .content(contentRepo.getById(dataDto.getContentId()))
                .date(dataDto.getDateName())
                .value(dataDto.getValue())
                .build();
    }

    public void addData(DataDto dataDto) {
        var data = dtoToEntity(dataDto);
        Example<Data> example = Example.of(data);
        if (!dataRepo.exists(example)) {
            try {
                dataRepo.save(dtoToEntity(dataDto));
            } catch (Exception e) {
                System.out.println("Error saving data in database");
                throw e;
            }
        }
    }

    public List<Integer> getTotal(Long contentId, Long estimateId, String companyName){
        return dataRepo.getTotals(contentId, estimateId, companyName);
    }

    public List<String> getCompanies(){
        return dataRepo.getCompanies();
    }
}
