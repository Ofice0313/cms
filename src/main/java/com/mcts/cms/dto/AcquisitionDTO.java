package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Acquisition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcquisitionDTO {

    private Long id;
    @JsonProperty("purchase_value")
    private BigDecimal purchaseValue;
    private BigDecimal travel;
    private Long vehicle;

    public AcquisitionDTO(Acquisition entity) {
        id = entity.getId();
        purchaseValue = entity.getPurchaseValue();
        travel = entity.getTravel();
        vehicle = entity.getVehicle().getId();
    }
}
