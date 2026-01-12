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
public class AcquisitionVehicleDTO {

    private Long id;
    @JsonProperty("purchase_value")
    private BigDecimal purchaseValue;
    private BigDecimal travel;

    @JsonProperty("vehicle_id")
    private VehicleDTO vehicleDTO;

    private BigDecimal total;

    public AcquisitionVehicleDTO(Acquisition entity) {
        id = entity.getId();
        purchaseValue = entity.getPurchaseValue();
        travel = entity.getTravel();
        total = entity.getTotal();
        vehicleDTO = new VehicleDTO(entity.getVehicle());
    }
}
