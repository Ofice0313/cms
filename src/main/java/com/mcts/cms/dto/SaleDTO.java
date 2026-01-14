package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {

    private Long id;
    @JsonProperty("sale_value")
    private BigDecimal saleValue;
    @JsonProperty("sale_date")
    private LocalDate saleDate;
    private String observations;

    private BigDecimal profit;

    private Long vehicle;

    private Long client;

    public SaleDTO(Sale entity) {
        this.id = entity.getId();
        this.saleDate = entity.getSaleDate();
        this.observations = entity.getObservations();
        this.saleValue = entity.getSaleValue();
        this.profit = entity.getProfit();
        this.client = entity.getClient().getId();
    }
}
