package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.enuns.StatusVehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleStockDTO {

    @JsonProperty("brand_model")
    private String brandModel;

    @JsonProperty("manufactured_year")
    private Integer manufacturedYear;

    private StatusVehicle status;

    private BigDecimal investment;
}