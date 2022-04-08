package com.example.liberumtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DataDto {
    private Long dataId;
    private String companyName;
    private Long estimateId;
    private Long contentId;
    private String dateName;
    private Integer value;
}
