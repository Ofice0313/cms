package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Client;
import com.mcts.cms.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientSalesVehiclesDTO {

    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String phone;

    private List<SaleVehicleDTO> sales = new ArrayList<>();

    public ClientSalesVehiclesDTO(Client entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();

        if (entity.getBuys() != null) {
            this.sales = entity.getBuys().stream()
                    .map(SaleVehicleDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleVehicleDTO {

        private Long id;
        @JsonProperty("sale_value")
        private BigDecimal saleValue;
        @JsonProperty("sale_date")
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate saleDate;
        private String observations;
        private BigDecimal diversos;
        @JsonProperty("sale_value_with_diversos")
        private BigDecimal saleValueWithDiversos;
        private BigDecimal profit;
        private VehicleDTO vehicle;

        public SaleVehicleDTO(Sale entity) {
            this.id = entity.getId();
            this.saleValue = entity.getSaleValue();
            this.saleDate = entity.getSaleDate();
            this.observations = entity.getObservations();
            this.diversos = entity.getDiversos();
            this.saleValueWithDiversos = entity.getSaleValueWithDiversos();
            this.profit = entity.getProfit();
            if (entity.getVehicle() != null) {
                this.vehicle = new VehicleDTO(entity.getVehicle());
            }
        }
    }
}
