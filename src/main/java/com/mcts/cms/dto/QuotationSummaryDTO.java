package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationSummaryDTO {

    private Long id;

    private String brand;

    private String model;

    @JsonProperty("manufactured_year")
    private Integer year;

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("client_phone")
    private String clientPhone;
}