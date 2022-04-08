package com.example.liberumtest.repo;

import com.example.liberumtest.models.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepo extends JpaRepository<Data, Long> {

    @Query("select d.company from Data d group by d.company")
    List<String> getCompanies();

    @Query("select sum(d.value) from Data d where d.content.id=?1 and d.estimate.id=?2 and d.company=?3 group by d.date")
    List<Integer> getTotals(Long content, Long estimate, String companyName);
}
