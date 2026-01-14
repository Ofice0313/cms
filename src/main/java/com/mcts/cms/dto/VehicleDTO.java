package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.entities.enuns.StatusVehicle;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private Long id;

    private String brand;

    private String model;

    @JsonProperty("manufactured_year")
    private Integer year;

    @JsonProperty("acquisition_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate acquisitionDate;

    @JsonProperty("registration_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate registrationDate;

    private StatusVehicle status;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @JsonProperty("purchase_value")
    private BigDecimal purchaseValue;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal travel;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal rights;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal cp;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal innater;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal loading;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @JsonProperty("customs_broker")
    private BigDecimal customsBroker;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal driver;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal inspection;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @JsonProperty("license_plate")
    private BigDecimal licensePlate;

    @Column(name = "order_date")
    private LocalDate orderDate = LocalDate.now();

    private BigDecimal acquisition;

    private BigDecimal order;

    private BigDecimal investment;

    public VehicleDTO(Vehicle entity) {
        this.id = entity.getId();
        this.brand = entity.getBrand();
        this.model = entity.getModel();
        this.year = entity.getYear();
        this.acquisitionDate = entity.getAcquisitionDate();
        this.registrationDate = entity.getRegistrationDate();
        this.status = entity.getStatus();
        this.cp = entity.getCp();
        this.purchaseValue = entity.getPurchaseValue();
        this.driver = entity.getDriver();
        this.travel = entity.getTravel();
        this.rights = entity.getRights();
        this.innater = entity.getInnater();
        this.loading = entity.getLoading();
        this.customsBroker = entity.getCustomsBroker();
        this.inspection = entity.getInspection();
        this.licensePlate = entity.getLicensePlate();
        this.acquisition = entity.getSubTotalAcquisition();
        this.order = entity.getSubTotalOrder();
        this.investment = entity.getTotalInvestment();
    }

}
