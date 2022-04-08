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

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "estimate", nullable = false)
    private Estimate estimate;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "content", nullable = false)
    private Content content;

    private String date;

    private Integer value;
}
