package com.example.liberumtest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Data")
@Table(name = "data")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long data_id;
    private String company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate", nullable = false)
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content", nullable = false)
    private Content content;
    private String date;
    private Integer value;

    @Override
    public int hashCode() {
        return Objects.hash(id, data_id, company, estimate, content, date, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        var data = (Data) obj;
        return data_id.equals(data.getData_id()) &&
                company.equals(data.getCompany()) &&
                estimate.equals(data.getEstimate()) &&
                content.equals(data.getContent()) &&
                date.equals(data.getDate()) &&
                value.equals(data.getValue());
    }
}
