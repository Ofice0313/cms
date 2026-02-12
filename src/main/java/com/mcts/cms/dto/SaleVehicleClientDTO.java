package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleVehicleClientDTO {

    private Long id;
    @JsonProperty("sale_value")
    private BigDecimal saleValue;
    @JsonProperty("sale_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate saleDate;
    private String observations;

//    @JsonProperty("client_id")
//    private Long clientId;
//
//    @JsonProperty("vehicle_id")
//    private Long vehicleId;

    @JsonProperty("client_id")
    private ClientDTO client;

    @JsonProperty("vehicle_id")
    private VehicleDTO vehicle;

    private BigDecimal profit;

    public SaleVehicleClientDTO(Sale entity) {
        this.id = entity.getId();
        this.saleDate = entity.getSaleDate();
        this.observations = entity.getObservations();
        this.saleValue = entity.getSaleValue();
        this.profit = entity.getProfit();
        this.client = new ClientDTO(entity.getClient());
        this.vehicle = new VehicleDTO(entity.getVehicle());
//        this.clientId = entity.getClient() != null ? entity.getClient().getId() : null;
//        this.vehicleId = entity.getVehicle() != null ? entity.getVehicle().getId() : null;
    }
}
