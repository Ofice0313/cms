package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private BigDecimal rights;
    private BigDecimal cp;
    private BigDecimal innater;
    private BigDecimal loading;
    @JsonProperty("customs_broker")
    private BigDecimal customsBroker;
    private BigDecimal driver;
    private BigDecimal inspection;
    @JsonProperty("license_plate")
    private BigDecimal licensePlate;
    @JsonProperty("order_date")
    private LocalDate orderDate = LocalDate.now();

    private Long acquisition;

    private Long vehicle;

    private BigDecimal total;

    private BigDecimal investment;

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.rights = entity.getRights();
        this.cp = entity.getCp();
        this.innater = entity.getInnater();
        this.loading = entity.getLoading();
        this.customsBroker = entity.getCustomsBroker();
        this.driver = entity.getDriver();
        this.inspection = entity.getInspection();
        this.licensePlate = entity.getLicensePlate();
        this.orderDate = entity.getOrderDate();
        this.acquisition = entity.getAcquisition().getId();
        this.vehicle = entity.getVehicle().getId();
        this.total = entity.getTotal();
        this.investment = entity.getInvestment();
    }
}
